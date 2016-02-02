package com.example.vincent.todaynews.tools;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class BaseTools {

	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	public final static int getWidthForImage(Context context,int mColumnCount) {
		return (ViewUtils.getScreenWidthPixels(context) - ViewUtils.dip2px(context, 10 + 6 * mColumnCount)) / mColumnCount;
	}
}
