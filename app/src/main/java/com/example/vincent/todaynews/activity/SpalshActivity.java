package com.example.vincent.todaynews.activity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.vincent.todaynews.Base.BaseActivity;
import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.fragment.ContentFragment;

/**
 * Created by vincent on 15/12/30.
 */
public class SpalshActivity extends BaseActivity {

    private AlphaAnimation splash_anima;
    private ImageView splashImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        splashImageView = (ImageView) findViewById(R.id.splash_imageview);
    }

    @Override
    protected void initView() {
        splash_anima = new AlphaAnimation(0.3f, 1.0f);
        splash_anima.setDuration(1000);
        splashImageView.startAnimation(splash_anima);
        splash_anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                openActivity(MainActivity.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
