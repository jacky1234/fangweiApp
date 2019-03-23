package com.jacky.lebeauty.ui.widget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 2018/11/9.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class GridLayoutPaddingItemDecoration extends RecyclerView.ItemDecoration {
    private int padding;

    public GridLayoutPaddingItemDecoration(int padding) {
        this.padding = padding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);
        outRect.set(padding / 2, padding / 2, padding / 2, padding / 2);
    }

}
