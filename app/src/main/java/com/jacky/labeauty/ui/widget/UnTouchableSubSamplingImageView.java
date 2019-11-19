package com.jacky.labeauty.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class UnTouchableSubSamplingImageView extends SubsamplingScaleImageView {
    public UnTouchableSubSamplingImageView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public UnTouchableSubSamplingImageView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return false;
    }
}
