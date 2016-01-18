package com.example.vincent.todaynews.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vincent.todaynews.Mock.Constants;
import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.adapter.NewsFragmentPagerAdapter;
import com.example.vincent.todaynews.bean.NewsClassify;
import com.example.vincent.todaynews.tools.BaseTools;
import com.example.vincent.todaynews.widget.ColumnHorizontalScrollView;

import java.util.ArrayList;

import dalvik.system.DexClassLoader;

public class ContentFragment extends Fragment implements View.OnClickListener {

    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    LinearLayout mRadioGroup_content;
    LinearLayout ll_more_columns;
    RelativeLayout rl_column;
    private ViewPager mViewPager;
    private ImageView button_more_columns;
    public ImageView shade_left;
    public ImageView shade_right;
    private ImageView top_head;
    private DrawerLayout drawerLayout;

    private int columnSelectIndex = 0;
    private int mScreenWidth = 0;
    private int mItemWidth = 0;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ArrayList<NewsClassify> newsClassify = new ArrayList<NewsClassify>();

    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            mViewPager.setCurrentItem(position);
            selectTab(position);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_content, container, false);
        findViewById(view);
        initView();
        return view;
    }

    private void findViewById(View view) {
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) view.findViewById(R.id.mColumnHorizontalScrollView);
        mRadioGroup_content = (LinearLayout) view.findViewById(R.id.mRadioGroup_content);
        ll_more_columns = (LinearLayout) view.findViewById(R.id.ll_more_columns);
        rl_column = (RelativeLayout) view.findViewById(R.id.rl_column);
        button_more_columns = (ImageView) view.findViewById(R.id.button_more_columns);
        mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);
        shade_left = (ImageView) view.findViewById(R.id.shade_left);
        shade_right = (ImageView) view.findViewById(R.id.shade_right);
        top_head = (ImageView) view.findViewById(R.id.top_head);
        top_head.setOnClickListener(this);
        button_more_columns.setOnClickListener(this);
    }

    protected void initView() {
        mScreenWidth = BaseTools.getWindowsWidth(getActivity());
        mItemWidth = mScreenWidth / 6 - 15;
        initColumnData();
        initTabColumn();
        initFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_head:
                drawerLayout.openDrawer(Gravity.LEFT);
            default:
                break;
        }
    }

    private void initColumnData() {
        newsClassify = Constants.getNewsData();
    }

    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
        int count = newsClassify.size();
        mColumnHorizontalScrollView.setParam(getActivity(), mScreenWidth, mRadioGroup_content, shade_left, shade_right, ll_more_columns, rl_column);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 8;
            params.rightMargin = 8;
            TextView localTextView = new TextView(getActivity());
            localTextView.setTextSize(17);
            localTextView.setGravity(Gravity.CENTER);
            localTextView.setPadding(5, 0, 5, 0);
            localTextView.setId(i);
            TextPaint tp = localTextView.getPaint();
            tp.setFakeBoldText(true);
            localTextView.setText(newsClassify.get(i).getTitle());

            if (columnSelectIndex == i) {
                localTextView.setSelected(true);
                localTextView.setTextColor(Color.RED);
            }
            localTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        TextView localView = (TextView) mRadioGroup_content.getChildAt(i);
                        if (localView != v) {
                            localView.setSelected(false);
                            localView.setTextColor(Color.BLACK);
                        } else {
                            localView.setSelected(true);
                            localView.setTextColor(Color.RED);
                            mViewPager.setCurrentItem(i);
                        }
                    }
                    Toast.makeText(getActivity(), newsClassify.get(v.getId()).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            mRadioGroup_content.addView(localTextView, i, params);
        }
    }

    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
        }
        for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
            View checkView = mRadioGroup_content.getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
            } else {
                ischeck = false;
            }
            checkView.setSelected(ischeck);
        }
    }

    private void initFragment() {
        int count = newsClassify.size();
        for (int i = 0; i < count; i++) {
            Bundle data = new Bundle();
            data.putString("text", newsClassify.get(i).getTitle());
            NewsFragment newfragment = new NewsFragment();
            newfragment.setArguments(data);
            fragments.add(newfragment);
        }
        NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(getFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapetr);
        mViewPager.addOnPageChangeListener(pageListener);
    }

    /**
     * 接受传递进来的drawer_layout
     *
     * @param drawer_layout
     */
    public void setDrawerLayout(DrawerLayout drawer_layout) {
        this.drawerLayout = drawer_layout;
    }
}
