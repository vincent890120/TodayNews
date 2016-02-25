/**
 * @file PullToRefreshListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * Implement OnRefreshListener,OnLoadMoreListener, and see onRefreshComplete() / onLoadMoreComplete().
 */
package com.example.vincent.todaynews.widget.pulltorefresh;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.widget.pulltorefresh.listview.ListViewFooter;
import com.example.vincent.todaynews.widget.pulltorefresh.listview.ListViewHeader;


public class PullToRefreshListView extends ListView implements OnScrollListener {

    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;
    private final static int SCROLL_DURATION = 400; // scroll back duration
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull

    public static final int DISABLE = 0;
    public static final int PULL_NO_REFRESH = 1;
    public static final int PULL_REFRESH = 2;

    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener
    // the interface to trigger refresh and load more.
    private OnRefreshListener mRefreshListener;
    private OnLoadMoreListener mLoadMoreListener;
    // -- header view
    private ListViewHeader mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private LinearLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight; // header view's height
    private int mPullRefreshState = PULL_REFRESH;
    private boolean mPullRefreshing = false; // is refreashing.
    // -- footer view
    private ListViewFooter mFooterView;
    private int mPullLoadState = PULL_NO_REFRESH;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;
    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;
    // for mScroller, scroll back from header or footer.
    private int mScrollBack;
    // feature.

    private boolean isHeaderRefresh = false;
    private boolean isScrool = false;


    private Handler mHandler;

    /**
     * @param context
     */
    public PullToRefreshListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        setOverScrollMode(OVER_SCROLL_NEVER);
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);

        mHandler = new Handler();

        // init header view
        mHeaderView = new ListViewHeader(context);
        mHeaderViewContent = (LinearLayout) mHeaderView
                .findViewById(R.id.listview_header_content);
        mHeaderTimeView = (TextView) mHeaderView
                .findViewById(R.id.listview_header_time);
        addHeaderView(mHeaderView);

        // init footer view
        mFooterView = new ListViewFooter(context);

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });

        setPullLoadEnable(DISABLE);
    }

    public ListViewFooter getFooter() {
        return this.mFooterView;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        if (mIsFooterReady == false) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param state
     */
    public void setPullRefreshEnable(int state) {
        mPullRefreshState = state;
        if (state == 2) { // disable, hide the content
            mHeaderViewContent.setVisibility(View.VISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param state
     */
    public void setPullLoadEnable(int state) {
        mPullLoadState = state;
        if (mPullLoadState == 2) {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(ListViewFooter.STATE_NORMAL);
            //make sure "pull up" don't show a line in bottom when listview with one page
            setFooterDividersEnabled(true);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        } else {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
            //make sure "pull up" don't show a line in bottom when listview with one page
            setFooterDividersEnabled(false);
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void onRefreshComplete() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mPullRefreshing == true) {
                    mPullRefreshing = false;
                    resetHeaderHeight();
                }
            }
        }, 500);
    }

    /**
     * stop load more, reset footer view.
     */
    public void onLoadMoreComplete() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(ListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * set last refresh time
     *
     * @param time
     */
    public void setRefreshTime(String time) {
        mHeaderTimeView.setText(time);
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnRefreshScrollListener) {
            OnRefreshScrollListener l = (OnRefreshScrollListener) mScrollListener;
            l.onScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight((int) delta
                + mHeaderView.getVisiableHeight());
        if (mPullRefreshState == PULL_REFRESH && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(ListViewHeader.STATE_READY);
            } else {
                mHeaderView.onPullImpl((float) mHeaderView.getVisiableHeight() / mHeaderViewHeight);
                mHeaderView.setState(ListViewHeader.STATE_NORMAL);
            }
        }
        setSelection(0); // scroll to top each time
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        isScrool = true;
        // trigger computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mPullLoadState == PULL_REFRESH && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
                // more.
                mFooterView.setState(ListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(ListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);

//		setSelection(mTotalItemCount - 1); // scroll to bottom
    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(ListViewFooter.STATE_LOADING);
        if (mLoadMoreListener != null) {
            mLoadMoreListener.onLoadMore(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (mPullRefreshState != DISABLE && getFirstVisiblePosition() == 0
                        && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                } else if (mPullLoadState != DISABLE && (getLastVisiblePosition() == mTotalItemCount - 1
                        && (mFooterView.getBottomMargin() > 0 || deltaY < 0))) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                mLastY = -1; // reset
                if (mPullRefreshState != DISABLE && getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (mPullRefreshState == PULL_REFRESH
                            && mHeaderView.getVisiableHeight() > mHeaderViewHeight && !mPullRefreshing) {
                        mPullRefreshing = true;
                        mHeaderView.setState(ListViewHeader.STATE_REFRESHING);
                        isHeaderRefresh = true;
                    }
                    resetHeaderHeight();
                } else if (mPullLoadState != DISABLE && getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mPullLoadState == PULL_REFRESH
                            && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA
                            && !mPullLoading) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        } else {
            if (isHeaderRefresh && mRefreshListener != null) {
                mRefreshListener.onRefresh(this);
                isHeaderRefresh = false;
            } else if (isScrool && !mPullRefreshing) {
                mHeaderView.reset();
                isScrool = false;
            }
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    public void setOnRefreshListener(OnRefreshListener l) {
        mRefreshListener = l;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener l) {
        mLoadMoreListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnRefreshScrollListener extends OnScrollListener {
        public void onScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface OnRefreshListener {
        public void onRefresh(PullToRefreshListView listView);
    }

    public interface OnLoadMoreListener {
        public void onLoadMore(PullToRefreshListView listView);
    }

    /*
    *为了兼容原来的下拉刷新控件，目前支持DISABLED和PULL_FROM_START
    * @param Mode.DISABLED Mode.PULL_FROM_START
     */
    public void setMode(PullToRefreshBase.Mode mode) {
        if (mode == PullToRefreshBase.Mode.DISABLED) {
            setPullRefreshEnable(DISABLE);
            setPullLoadEnable(DISABLE);
        } else if (mode == PullToRefreshBase.Mode.PULL_FROM_START) {
            setPullRefreshEnable(PULL_REFRESH);
            setPullLoadEnable(DISABLE);
        }
    }

    public void setHeaderBackgroundImage(int resid) {
        mHeaderView.setBackgroundImage(resid);
    }

    public void setRefreshing() {
        if (mPullRefreshState == PULL_REFRESH) {
            mPullRefreshing = true;
            mHeaderView.setState(ListViewHeader.STATE_REFRESHING);
            if (mRefreshListener != null) {
                mRefreshListener.onRefresh(this);
            }

            mScrollBack = SCROLLBACK_HEADER;
            mScroller.startScroll(0, 0, 0, mHeaderViewHeight, SCROLL_DURATION);
            // trigger computeScroll
            invalidate();
        }
    }
}
