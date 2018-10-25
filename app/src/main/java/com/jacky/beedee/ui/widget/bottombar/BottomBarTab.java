package com.jacky.beedee.ui.widget.bottombar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.module.skin.DefaultSkinLoader;
import com.jacky.beedee.R;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class BottomBarTab extends LinearLayout {
    private ImageView mIcon;
    private TextView mTvTitle;
    private int mTabPosition = -1;
    private int iconNormal, iconSelect;
    private int colorNormal, colorSelect;

    private TextView mTvUnreadCount;
    private View dot;

    @SuppressLint("ResourceAsColor")
    public BottomBarTab(Context context,
                        @DrawableRes int iconNormal,
                        @DrawableRes int iconSelect,
                        CharSequence title) {
        this(context, null, iconNormal, iconSelect, title, R.color.tabbar_text_color, R.color.tabbar_text_hig_color);
    }

    public BottomBarTab(Context context,
                        @DrawableRes int iconNormal,
                        @DrawableRes int iconSelect,
                        CharSequence title,
                        @ColorInt int colorNormal,
                        @ColorInt int colorSelect) {
        this(context, null, iconNormal, iconSelect, title, colorNormal, colorSelect);
    }

    public BottomBarTab(Context context,
                        AttributeSet attrs,
                        int iconNormal,
                        int iconSelect,
                        CharSequence title,
                        @ColorInt int colorNormal,
                        @ColorInt int colorSelect) {
        this(context, attrs, 0, iconNormal, iconSelect, title, colorNormal, colorSelect);
    }

    public BottomBarTab(Context context,
                        AttributeSet attrs,
                        int defStyleAttr,
                        int iconNormal,
                        int iconSelect,
                        CharSequence title,
                        @ColorInt int colorNormal,
                        @ColorInt int colorSelect) {
        super(context, attrs, defStyleAttr);
        init(context, iconNormal, iconSelect, title, colorNormal, colorSelect);
    }

    private void init(Context context, int iconNormal, int iconSelect, CharSequence title, int colorNormal, int colorSelect) {
        this.iconNormal = iconNormal;
        this.iconSelect = iconSelect;
        this.colorNormal = colorNormal;
        this.colorSelect = colorSelect;
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();

        LinearLayout lLContainer = new LinearLayout(context);
        lLContainer.setOrientation(LinearLayout.VERTICAL);
        lLContainer.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams paramsContainer = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsContainer.gravity = Gravity.CENTER;
        lLContainer.setLayoutParams(paramsContainer);

        mIcon = new ImageView(context);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
//        mIcon.setImageResource(iconNormal);
        setImageIcon(iconNormal);
        mIcon.setLayoutParams(params);
//        mIcon.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect));
        lLContainer.addView(mIcon);

        mTvTitle = new TextView(context);
        mTvTitle.setText(title);
        LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTv.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        mTvTitle.setTextSize(10);
        setTitleColor(colorNormal);
        mTvTitle.setLayoutParams(paramsTv);
        lLContainer.addView(mTvTitle);
        addView(lLContainer);

        int min = dip2px(context, 20);
        int padding = dip2px(context, 2);
        mTvUnreadCount = new TextView(context);
        mTvUnreadCount.setBackgroundResource(R.drawable.spot_bg);
        mTvUnreadCount.setMinWidth(min);
        mTvUnreadCount.setTextColor(R.color.spot_text_color);
        mTvUnreadCount.setTextSize(10f);
        mTvUnreadCount.setPadding(padding, 0, padding, 0);
        mTvUnreadCount.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams tvUnReadParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, min);
        tvUnReadParams.gravity = Gravity.CENTER;
        tvUnReadParams.leftMargin = dip2px(context, 17);
        tvUnReadParams.bottomMargin = dip2px(context, 14);
        mTvUnreadCount.setLayoutParams(tvUnReadParams);
        mTvUnreadCount.setVisibility(GONE);
        addView(mTvUnreadCount);

        int dotWidth = dip2px(context, 10);
        dot = new View(context);
        setBadgeBackGroundDrawable(R.drawable.spot_bg);
        FrameLayout.LayoutParams dotParams = new FrameLayout.LayoutParams(dotWidth, dotWidth);
        dotParams.gravity = Gravity.CENTER;
        dotParams.leftMargin = dip2px(context, 15);
        dotParams.bottomMargin = dip2px(context, 16);
        dot.setLayoutParams(dotParams);
        dot.setVisibility(GONE);
        addView(dot);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
//            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
//            mIcon.setImageResource(iconSelect);
            setImageIcon(iconSelect);
//            mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.tab_bar_title_sel_color));
            setTitleColor(colorSelect);
        } else {
//            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.tab_unselect));
//            mIcon.setImageResource(iconNormal);
            setImageIcon(iconNormal);
//            mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.tab_bar_title_nor_color));
            setTitleColor(colorNormal);
        }
    }

    public void setImageIcon(int resId) {
        mIcon.setImageDrawable(DefaultSkinLoader.getInstance().getDrawable(resId));
    }

    public void setTitleColor(int colorId) {
        mTvTitle.setTextColor(DefaultSkinLoader.getInstance().getColor(colorId));
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }

    /**
     * 设置未读数量
     */
    public void setUnreadCount(int num) {
        dot.setVisibility(GONE);
        if (num <= 0) {
            mTvUnreadCount.setText(String.valueOf(0));
            mTvUnreadCount.setVisibility(GONE);
        } else {
            mTvUnreadCount.setVisibility(VISIBLE);
            if (num > 99) {
                mTvUnreadCount.setText("99+");
            } else {
                mTvUnreadCount.setText(String.valueOf(num));
            }
        }
    }

    /**
     * 设置小红点
     */
    public void setRedDot(int num) {
        mTvUnreadCount.setVisibility(GONE);
        dot.setVisibility(num >= 0 ? VISIBLE : GONE);
    }

    public void setBadgeBackGroundDrawable(int resId) {
        if (dot != null) {
            dot.setBackground(DefaultSkinLoader.getInstance().getDrawable(resId));
        }
        if (mTvUnreadCount != null) {
            mTvUnreadCount.setBackground(DefaultSkinLoader.getInstance().getDrawable(resId));
        }
    }

    /**
     * 获取当前未读数量
     */
    public int getUnreadCount() {
        int count = 0;
        if (TextUtils.isEmpty(mTvUnreadCount.getText())) {
            return count;
        }
        if ("99+".equals(mTvUnreadCount.getText().toString())) {
            return 99;
        }
        try {
            count = Integer.valueOf(mTvUnreadCount.getText().toString());
        } catch (Exception ignored) {
        }
        return count;
    }

    private int dip2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
