package com.example.vincent.todaynews.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by vincent on 16/2/22.
 */
public class MesasuredListView extends ListView{
    public MesasuredListView(Context context) {
        super(context);
    }

    public MesasuredListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MesasuredListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
