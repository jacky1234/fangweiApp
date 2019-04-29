package com.jacky.labeauty.ui.widget.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 目前只简单支持 LinearlayoutManager
 */
public class DividerPaddingDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private int mOrientation;
    private Context context;
    private int itemPadding = 0;

    public DividerPaddingDecoration(Context context, int orientation, int itemPadding) {
        this.context = context;
        this.itemPadding = itemPadding;
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, itemPadding, 0, 0);
        } else {
            outRect.set(itemPadding, itemPadding, 0, 0);
        }
        final int position = parent.getChildAdapterPosition(view);
        if (position == parent.getChildCount() - 1) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.bottom = itemPadding;
            } else {
                outRect.right = itemPadding;
            }
        }
    }
}
