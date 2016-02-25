/**
 * @file ListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description ListView's header
 */
package com.example.vincent.todaynews.widget.pulltorefresh.listview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vincent.todaynews.R;


public class ListViewHeader extends LinearLayout {
    public

    static final int TOTAL_FRAME = 10;
    public final static int STATE_NORMAL = 0;
    private int mState = STATE_NORMAL;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;
    private LinearLayout mContainer;
    private ImageView mHeaderImage;
    private TextView mHintTextView;
    private ImageView mBackgroundImage;

    private Matrix mHeaderImageMatrix;

    private float mPivotX, mPivotY;

    public ListViewHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public ListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(
                LayoutParams.FILL_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.listview_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

        mBackgroundImage= (ImageView) findViewById(R.id.listview_header_background);

        mHeaderImage = (ImageView) findViewById(R.id.listview_header_image);
        mHintTextView = (TextView) findViewById(R.id.listview_header_hint_textview);

        Drawable img=getResources().getDrawable(getDefaultDrawableResId());
        mHeaderImage.setImageDrawable(img);
        mHeaderImage.setScaleType(ImageView.ScaleType.FIT_XY);
        mHeaderImageMatrix = new Matrix(mHeaderImage.getImageMatrix());

        onLoadingDrawableSet(img);
    }

    public void setState(int state) {
        if (state == mState) return;

        switch (state) {
            case STATE_NORMAL:
                mHintTextView.setText(R.string.listview_header_hint_normal);
                break;
            case STATE_READY:
                mHeaderImageMatrix.setScale(1, 1, mPivotX, mPivotY);
                mHeaderImage.setImageMatrix(mHeaderImageMatrix);
                // 此处调用动画播放方法
                mHintTextView.setText(R.string.listview_header_hint_ready);
                break;
            case STATE_REFRESHING:
                mHintTextView.setText(R.string.listview_header_hint_loading);
                mHeaderImage.setImageResource(R.anim.pull_loading);
                AnimationDrawable anim = (AnimationDrawable) mHeaderImage.getDrawable();
                anim.start();
                break;
            default:
        }

        mState = state;
    }

    public void onPullImpl(float scaleOfLayout){
        mHeaderImageMatrix.setScale(scaleOfLayout, scaleOfLayout, mPivotX,
                mPivotY);
        mHeaderImage.setImageMatrix(mHeaderImageMatrix);
        mHeaderImage.setImageResource((int) (scaleOfLayout / 1 * TOTAL_FRAME)
                + getDefaultDrawableResId());
    }

    public void onLoadingDrawableSet(Drawable imageDrawable) {
        if (null != imageDrawable) {
            mPivotX = Math.round(imageDrawable.getIntrinsicWidth() / 2f);
            mPivotY = Math.round(imageDrawable.getIntrinsicHeight() / 2f);
        }
    }

    public int getVisiableHeight() {
        return mContainer.getLayoutParams().height;
    }

    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public void reset(){
        mHeaderImage.setImageResource(getDefaultDrawableResId());
        mHeaderImage.clearAnimation();
        if (null != mHeaderImageMatrix) {
            mHeaderImageMatrix.reset();
            mHeaderImage.setImageMatrix(mHeaderImageMatrix);
        }
    }

    protected int getDefaultDrawableResId() {
        return R.drawable.dropdown_anim_00;
    }

    public void setBackgroundImage(int resid){
        mBackgroundImage.setBackgroundResource(resid);
    }

}
