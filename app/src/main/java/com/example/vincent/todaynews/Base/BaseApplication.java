package com.example.vincent.todaynews.base;

import android.app.Application;

import com.example.vincent.todaynews.imageloader.ImageLoaderConfig;

/**
 * Created by vincent on 15/12/30.
 */
public class BaseApplication extends Application{
    // 图片存储路径
    public static final String BASE_PATH ="/newstoday/";

    // 缓存图片路径
    public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfig.initImageLoader(this, BASE_IMAGE_CACHE);
    }
}
