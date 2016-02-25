package com.example.vincent.todaynews.widget.pulltorefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView.ScaleType;

import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.widget.pulltorefresh.PullToRefreshBase;


public class DperLoadingLayout extends LoadingLayout {

    static final int TOTAL_FRAME = 10;
    private final Matrix mHeaderImageMatrix;
    boolean isVisible = true;
    private float mRotationPivotX, mRotationPivotY;

    public DperLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection,
                             TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

        mHeaderImage.setScaleType(ScaleType.MATRIX);
        mHeaderImageMatrix = new Matrix(mHeaderImage.getImageMatrix());
    }

    public void onLoadingDrawableSet(Drawable imageDrawable) {
        if (null != imageDrawable) {
            mRotationPivotX = Math.round(imageDrawable.getIntrinsicWidth() / 2f);
            mRotationPivotY = Math.round(imageDrawable.getIntrinsicHeight() / 2f);
        }
    }

    protected void onPullImpl(float scaleOfLayout) {
        if (!isVisible)
            return;

        if (scaleOfLayout <= 1) {
            mHeaderImageMatrix.setScale(scaleOfLayout, scaleOfLayout, mRotationPivotX,
                    mRotationPivotY);
            mHeaderImage.setImageMatrix(mHeaderImageMatrix);
            mHeaderImage.setImageResource((int) (scaleOfLayout / 1 * TOTAL_FRAME)
                    + getDefaultDrawableResId());
        } else {
            mHeaderImageMatrix.setScale(1, 1, mRotationPivotX, mRotationPivotY);
            mHeaderImage.setImageMatrix(mHeaderImageMatrix);
            mHeaderImage.setImageResource(TOTAL_FRAME + getDefaultDrawableResId());
        }
    }

    @Override
    protected void refreshingImpl() {
        if (!isVisible)
            return;

        // 此处调用动画播放方法
        mHeaderImage.setImageResource(R.anim.pull_loading);
        AnimationDrawable anim = (AnimationDrawable) mHeaderImage.getDrawable();
        anim.start();
    }

    @Override
    protected void resetImpl() {
        mHeaderImage.clearAnimation();
        resetImageRotation();
    }

    private void resetImageRotation() {
        if (null != mHeaderImageMatrix) {
            mHeaderImageMatrix.reset();
            mHeaderImage.setImageMatrix(mHeaderImageMatrix);
        }
    }

    @Override
    protected void pullToRefreshImpl() {
        // NO-OP
    }

    @Override
    protected void releaseToRefreshImpl() {
        // NO-OP
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.dropdown_anim_00;
    }

    @Override
    public void setLoadingVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

}
