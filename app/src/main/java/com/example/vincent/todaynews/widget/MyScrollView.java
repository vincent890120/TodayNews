package com.example.vincent.todaynews.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


/**
 * 处理scrollview里面嵌套viewpager，导致两个控件滑动冲突问题。<br>
 * 提供OnScrollListener
 *
 * @author yutao
 */
public class MyScrollView extends ScrollView {

    private float xDistance, yDistance, lastX, lastY;
    private OnScrollListener scrollListener;
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        scrollListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                lastX = ev.getX();
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - lastX);
                yDistance += Math.abs(curY - lastY);
                lastX = curX;
                lastY = curY;
                if (xDistance > yDistance)
                    return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (scrollListener != null) {
            scrollListener.onScroll(l, t, oldl, oldt);
        }
    }

    public static interface OnScrollListener {
        void onScroll(int l, int t, int oldl, int oldt);
    }

}
