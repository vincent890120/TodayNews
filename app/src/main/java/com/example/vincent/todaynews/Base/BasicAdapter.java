package com.example.vincent.todaynews.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vincent.todaynews.R;

/**
 * Created by vincent on 16/2/22.
 */
public abstract class BasicAdapter extends BaseAdapter{
    public static final Object EMPTY = new Object();
    public static final Object LOADING = new Object();
    public static final Object ERROR = new Object();

    public int FLAG = 0;  //0 正常，1 无数据，2网路错误

    /**
     * 加载界面
     * @param parent
     * @param convertView
     * @return
     */
    protected View getLoadingView(ViewGroup parent, View convertView) {
        View v = convertView == null ? null : convertView.getTag() == LOADING ? convertView : null;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.list_load_layout, parent, false);
            v.setTag(LOADING);
        }
        return v;
    }

    /**
     * 数据为空或者网络错误界面
     * @param parent
     * @param convertView
     * @return
     */
    protected View getEmptyView(ViewGroup parent, View convertView) {
        String msg;
        View v = convertView == null ? null : convertView.getTag() == EMPTY ? convertView : null;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.list_empty_layout, parent, false);
            v.setTag(EMPTY);
        }
        msg = FLAG == 2 ? "网络连接失败" : "暂无相关数据";

        ((TextView) v.findViewById(android.R.id.text1)).setText(msg);
        return v;
    }
}
