package com.jacky.beedee.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.jacky.beedee.R

/**
 * 2018/10/27.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
class TitleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private var iv_left_icon: ImageView
    private var tv_left_text: TextView
    private var tv_title: TextView
    private var left_container: View

    private var leftDrawableId: Int
    private var leftTextId: Int
    private var leftTextColorId: Int
    private var middleTextId: Int
    private var middleTextColorId: Int

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.title_view, null)
        addView(view)

        iv_left_icon = view.findViewById(R.id.iv_left_icon)
        tv_left_text = view.findViewById(R.id.tv_left_text)
        tv_title = view.findViewById(R.id.tv_title)
        left_container = view.findViewById(R.id.left_container)


        var ta: TypedArray? = null
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.TitleView)
            leftDrawableId = ta.getResourceId(R.styleable.TitleView_titleView_left_drawable, R.mipmap.ic_arrow_back_black)
            leftTextId = ta.getResourceId(R.styleable.TitleView_titleView_left_text, 0)
            middleTextId = ta.getResourceId(R.styleable.TitleView_titleView_title_text, 0)
            leftTextColorId = ta.getColor(R.styleable.TitleView_titleView_left_text_color, resources.getColor(android.R.color.white))
            middleTextColorId = ta.getColor(R.styleable.TitleView_titleView_middle_text_color, resources.getColor(android.R.color.white))
        } finally {
            ta?.recycle()
        }

        setLeftDrawableId(leftDrawableId)
        setLeftTextId(leftTextId)
        setLeftTextColorId(leftTextColorId)
        setMiddleTextId(middleTextId)
        setMiddleTextColorId(middleTextColorId)
    }

    fun setLeftDrawableId(leftDrawableId: Int) {
        this.leftDrawableId = leftDrawableId
        iv_left_icon.setImageResource(leftDrawableId)
    }

    fun setLeftTextId(leftTextId: Int) {
        this.leftTextId = leftTextId
        if (leftTextId == 0) {
            tv_left_text.visibility = View.INVISIBLE
        } else {
            tv_left_text.visibility = View.VISIBLE
            tv_left_text.setText(leftTextId)
        }
    }

    fun setLeftTextColorId(leftTextColorId: Int) {
        this.leftTextColorId = leftTextColorId
        tv_left_text.setTextColor(leftTextColorId)
    }

    fun setMiddleTextId(middleTextId: Int) {
        this.middleTextId = middleTextId
        if (middleTextId == 0) {
            tv_title.visibility = View.INVISIBLE
        } else {
            tv_title.visibility = View.VISIBLE
            tv_title.setText(middleTextId)
        }
    }

    fun setMiddleTextColorId(middleTextColorId: Int) {
        this.middleTextColorId = middleTextColorId
        tv_title.setTextColor(middleTextColorId)
    }

    fun setLeftAction(onClickListener: OnClickListener?) {
        left_container.setOnClickListener(onClickListener)
    }
}
