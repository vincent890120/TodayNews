package com.example.vincent.todaynews.Base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by vincent on 15/12/30.
 */
public abstract class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected abstract void findViewById();
    protected abstract void initView();
    protected void openActivity(Class<?> pClass ,Bundle bundle){
        Intent intent = new Intent(this,pClass);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    protected void openActivity(Class<?> pClass){
        openActivity(pClass,null);
    }
}
