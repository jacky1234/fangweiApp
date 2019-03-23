package com.jacky.lebeauty.ui.widget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
    private int paddingBottom;

    public BottomOffsetDecoration(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);
        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.set(0, 0, 0, paddingBottom);
        }
    }
}
