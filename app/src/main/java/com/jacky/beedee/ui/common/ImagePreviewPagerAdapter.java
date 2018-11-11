package com.jacky.beedee.ui.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class ImagePreviewPagerAdapter extends FragmentPagerAdapter {

    private List<Image> mItems = new ArrayList<>();
    private ImagePreviewPagerAdapter.OnPrimaryItemSetListener mListener;

    public ImagePreviewPagerAdapter(FragmentManager manager, @Nullable ImagePreviewPagerAdapter.OnPrimaryItemSetListener listener) {
        super(manager);
        mListener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        return ImagePreviewDetailFragment.newInstance(mItems.get(position));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (mListener != null) {
            mListener.onPrimaryItemSet(position);
        }
    }

    public Image getImage(int position) {
        return mItems.get(position);
    }

    public void addAll(List<Image> items) {
        mItems.addAll(items);
    }

    interface OnPrimaryItemSetListener {
        void onPrimaryItemSet(int position);
    }

}