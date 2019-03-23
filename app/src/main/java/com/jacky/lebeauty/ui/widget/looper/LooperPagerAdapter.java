package com.jacky.lebeauty.ui.widget.looper;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

/**
 * 2018/4/11.
 * github:[https://github.com/jacky1234]
 *
 * @author jackyang
 */

public class LooperPagerAdapter extends PagerAdapter {
    public interface CallBack {
        @NotNull
        View onCreateView(int position);
    }


    private int total;
    private CallBack callback;

    public LooperPagerAdapter(int total, @NotNull CallBack callback) {
        this.total = total;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return total;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        final View child = callback.onCreateView(position);
        view.addView(child, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return child;
    }

    /**
     * 兼容 https://github.com/ongakuer/CircleIndicator infinit end to start
     */


}
