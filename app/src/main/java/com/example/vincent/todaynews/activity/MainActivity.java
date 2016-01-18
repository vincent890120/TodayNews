package com.example.vincent.todaynews.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;


import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.fragment.ContentFragment;
import com.example.vincent.todaynews.fragment.LeftDrawerFragment;

/**
 * Created by vincent on 16/1/6.
 */
public class MainActivity extends FragmentActivity{

    private DrawerLayout drawer_layout;
    private LeftDrawerFragment leftDrawerFragment;
    private FragmentManager fManager;
    private ContentFragment contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initView();
    }


    protected void findViewById() {
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fManager = getSupportFragmentManager();
        leftDrawerFragment = (LeftDrawerFragment)fManager.findFragmentById(R.id.fg_left_drawer_left);
        contentFragment = (ContentFragment)fManager.findFragmentById(R.id.fg_content);
    }


    protected void initView() {
        leftDrawerFragment.setDrawerLayout(drawer_layout);
        contentFragment.setDrawerLayout(drawer_layout);
    }
}
