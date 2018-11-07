package com.jacky.beedee.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * 2018/11/7.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 *
 * see GridLayout
 */
class GridContainer @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ViewGroup(context, attrs, defStyleAttr) {

    private var spanCount = 2
    private var itemWidth = 300f
    private var itemPadding = 30f


    fun setItemPadding(itemPadding: Float) {
        this.itemPadding = itemPadding
        requestLayout()
    }

    fun setItemWidth(itemPadding: Float) {
        this.itemPadding = itemPadding
        requestLayout()
    }

    override fun addView(child: View?) {
        val layoutManager = ViewGroup.LayoutParams(itemWidth.toInt(), itemWidth.toInt())
        super.addView(child, layoutManager)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        for (i in 0..childCount) {
            getChildAt(i)?.layout((paddingLeft + (i % spanCount) * (itemWidth + itemPadding)).toInt(),
                    (paddingTop + (i / spanCount) * (itemWidth + itemPadding)).toInt(),
                    (paddingLeft + (i % spanCount) * (itemWidth + itemPadding) + itemWidth).toInt(),
                    (paddingTop + (i / spanCount) * (itemWidth + itemPadding) + itemWidth).toInt()
            )
        }
    }
}
