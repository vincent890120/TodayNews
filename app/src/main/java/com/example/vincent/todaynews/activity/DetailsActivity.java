package com.example.vincent.todaynews.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.base.BaseActivity;

/**
 * Created by vincent on 16/2/1.
 */
public class DetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetails);
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
