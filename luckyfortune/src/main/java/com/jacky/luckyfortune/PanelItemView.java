package com.jacky.luckyfortune;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;

//       try to :checkDrawable.setColorFilter(new PorterDuffColorFilter(0xffffffff, PorterDuff.Mode.MULTIPLY))
public class PanelItemView extends FrameLayout implements ItemView {

//    private View overlay;

    public PanelItemView(Context context) {
        this(context, null);
    }

    public PanelItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        inflate(context, R.layout.view_panel_item, this);
//        overlay = findViewById(R.id.overlay);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setFocus(boolean isFocused) {
//        Drawable drawable = getBackground();
//        final int color = isFocused ? 0x6400000 : 0x6400000;
//        if (drawable != null) {
//            drawable = DrawableCompat.wrap(drawable);
//            DrawableCompat.setTint(drawable, isFocused ? 0xf0ffffff : 0x6400000);
//            setBackground(drawable);
//        }
//        if (overlay != null) {
//            overlay.setVisibility(isFocused ? INVISIBLE : VISIBLE);
//        }
    }

}
