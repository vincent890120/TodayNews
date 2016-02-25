package com.example.vincent.todaynews.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

/**
 * Created by vincent on 15/12/30.
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void initView();

    protected void openActivity(Class<?> pClass, Bundle bundle) {
        Intent intent = new Intent(this, pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    public void doBack(View view) {
        onBackPressed();
    }

    public void showAlert(String message) {
        showAlert("提示", message, false, null, null);
    }

    public void showAlert(String title, String message, boolean hasCancelBtn, DialogInterface.OnClickListener lOk,
                          DialogInterface.OnClickListener lCancel) {

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        if (lOk == null) {
            lOk = listener;
        }
        if (lCancel == null) {
            lCancel = listener;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", lOk);
        if (hasCancelBtn) {
            builder.setNegativeButton("取消", lCancel);
        }
        builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public String getStringParam(String name) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                if (val != null)
                    return val;
            }
        } catch (Exception e) {
        }

        return i.getStringExtra(name);
    }

    public int getIntParam(String name) {
        return getIntParam(name, 0);
    }

    public int getIntParam(String name, int defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return Integer.parseInt(val);
            }
        } catch (Exception e) {
        }

        return i.getIntExtra(name, defaultValue);
    }

    public double getDoubleParam(String name, double defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return Double.parseDouble(val);
            }
        } catch (Exception e) {
        }

        return i.getDoubleExtra(name, defaultValue);
    }

    public double getDoubleParam(String name) {
        return getDoubleParam(name, 0);
    }
}
