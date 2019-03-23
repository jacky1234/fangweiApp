package com.jacky.sub.beedee.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jacky.sub.beedee.R;

public class RatioFrameLayout extends FrameLayout {

    private static final float NORMAL_RATIO = 1.0f;

    private float widthRatio;
    private float heightRatio;

    public RatioFrameLayout(@NonNull Context context) {
        super(context);
    }

    public RatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout, 0, 0);
        widthRatio = a.getFloat(R.styleable.RatioFrameLayout_widthRatio, 0);
        heightRatio = a.getFloat(R.styleable.RatioFrameLayout_heightRatio, 0);
        a.recycle();
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        params.width = getWidth();
        params.height = getHeight();
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width = MeasureSpec.getSize(widthMeasureSpec);
        float height = MeasureSpec.getSize(heightMeasureSpec);
        float realWidth = width;
        float realHeight = height;
        if (widthRatio == NORMAL_RATIO) {
            float ratio = heightRatio / widthRatio;
            realHeight = ratio * width;

        } else if (heightRatio == NORMAL_RATIO) {
            float ratio = widthRatio / heightRatio;
            realWidth = ratio * height;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec((int) realWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec((int) realHeight, MeasureSpec.EXACTLY));
    }
}