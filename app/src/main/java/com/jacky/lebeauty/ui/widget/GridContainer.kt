package com.jacky.lebeauty.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View.MeasureSpec.EXACTLY
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * 2018/11/7.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 *
 * see GridLayout
 */
class GridContainer @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private var spanCount = 2
    private var itemWidth = 10f
    private var itemGap = 10f
    private var ratio = 1.0f


    fun setItemPadding(itemPadding: Float): GridContainer {
        this.itemGap = itemPadding
        return this
    }

    fun setSpanCount(spanCount: Int): GridContainer {
        this.spanCount = spanCount
        return this
    }

    fun setRatio(ratio: Float): GridContainer {
        this.ratio = ratio
        return this
    }

    fun layout() {
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        if (widthMode == EXACTLY) {
            itemWidth = (widthSize - paddingLeft - paddingRight - (spanCount - 1) * itemGap) / spanCount

            val rowCount = if (childCount % spanCount != 0) (childCount / spanCount + 1) else childCount / spanCount
            val height = (paddingTop + paddingBottom + (rowCount - 1) * itemGap + rowCount * itemWidth).toInt()
            setMeasuredDimension(widthSize, height)
        }


        for (i in 0..childCount) {
            val child = this.getChildAt(i) ?: continue
            val lp = child.layoutParams

            lp.width = itemWidth.toInt()
            lp.height = itemWidth.toInt()

            val childWidthMeasureSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, lp.width)
            val childHeightMeasureSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec, 0, lp.height)

            this.measureChild(child, childWidthMeasureSpec, childHeightMeasureSpec)
        }
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        for (i in 0..childCount) {
            getChildAt(i)?.layout((paddingLeft + (i % spanCount) * (getItemWidth() + itemGap)).toInt(),
                    (paddingTop + (i / spanCount) * (getItemHeight() + itemGap)).toInt(),
                    (paddingLeft + (i % spanCount) * (getItemWidth() + itemGap) + getItemWidth()).toInt(),
                    (paddingTop + (i / spanCount) * (getItemHeight() + itemGap) + getItemHeight()).toInt()
            )
        }
    }

    private fun getItemWidth() = itemWidth
    private fun getItemHeight() = (1.0f / ratio) * itemWidth
}
