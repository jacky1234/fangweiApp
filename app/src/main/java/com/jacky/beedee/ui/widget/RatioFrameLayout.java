package com.jacky.beedee.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jacky.beedee.R;

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