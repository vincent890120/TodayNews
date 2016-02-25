package com.example.vincent.todaynews.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by vincent on 16/2/22.
 */
public abstract class BaseTodayNewsAdapter<T> extends BasicAdapter {
    public List<T> list;

    @Override
    public int getCount() {
        if (list.size() == 0) {
            return 1;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (position < list.size()) {
            return list.get(position);
        } else {
            return FLAG == 0 ? LOADING : FLAG == 1 ? EMPTY : ERROR;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object item = getItem(position);
        if (item.equals(LOADING)) {
            return getLoadingView(parent, convertView);
        } else if (item.equals(EMPTY) || item.equals(ERROR)) {
            return getEmptyView(parent, convertView);
        } else {
            return getItemView(convertView, parent, item, position);
        }
    }

    protected abstract View getItemView(View convertView, ViewGroup parent, Object obj, int position);

    public void resetList(List<T> list){
        this.list = list;
    }
}
