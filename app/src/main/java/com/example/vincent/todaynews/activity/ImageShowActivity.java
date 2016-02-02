package com.example.vincent.todaynews.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vincent.todaynews.ImageShowViewPager;
import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.adapter.ImagePagerAdapter;
import com.example.vincent.todaynews.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by vincent on 16/2/2.
 */
public class ImageShowActivity extends BaseActivity{
    /** 图片展示 */
    private ImageShowViewPager image_pager;
    private TextView page_number;
    /** 图片下载按钮 */
    private ImageView download;
    /** 图片列表 */
    private ArrayList<String> imgsUrl;
    /** PagerAdapter */
    private ImagePagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        initView();
        initData();
        initViewPager();
    }

    private void initData() {
        imgsUrl = getIntent().getStringArrayListExtra("infos");
        page_number.setText("1" + "/" + imgsUrl.size());
    }
    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {
        image_pager = (ImageShowViewPager) findViewById(R.id.image_pager);
        page_number = (TextView) findViewById(R.id.page_number);
        download = (ImageView) findViewById(R.id.download);
        image_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                page_number.setText((arg0 + 1) + "/" + imgsUrl.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void initViewPager() {
        if (imgsUrl != null && imgsUrl.size() != 0) {
            mAdapter = new ImagePagerAdapter(getApplicationContext(), imgsUrl);
            image_pager.setAdapter(mAdapter);
        }
    }
}
