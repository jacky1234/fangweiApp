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
import kotlinx.android.synthetic.main.title_view.view.*

/**
 * 2018/10/27.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
class TitleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        @JvmField
        val FLAG_NONE = 0
        const val FLAG_LEFT_VISIBLE = 1
        const val FLAG_MIDDLE_VISIBLE = 1 shl 1
        const val FLAG_RIGHT_VISIBLE = 1 shl 2


        //compose flag
        //左中可见
        const val COMPOSE_LEFT_MIDDLEL_FLAG = FLAG_LEFT_VISIBLE or FLAG_MIDDLE_VISIBLE
        //左中右都可见
        const val COMPOSE_ALL_FLAG = FLAG_LEFT_VISIBLE or FLAG_MIDDLE_VISIBLE or FLAG_RIGHT_VISIBLE
    }

    private var flag = COMPOSE_LEFT_MIDDLEL_FLAG
    private var iv_left_icon: ImageView
    private var tv_left_text: TextView
    private var tv_title: TextView
    private var left_container: View
    private var right_container: View

    private var leftDrawableId: Int
    private var leftTextId: Int
    private var leftTextColorId: Int
    private var middleTextId: Int
    private var middleTextColorId: Int
    private var rightTextId: Int
    private var rightTextColorId: Int

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.title_view, null)
        addView(view)

        iv_left_icon = view.findViewById(R.id.iv_left_icon)
        tv_left_text = view.findViewById(R.id.tv_left_text)
        tv_title = view.findViewById(R.id.tv_title)
        left_container = view.findViewById(R.id.left_container)
        right_container = view.findViewById(R.id.right_container)


        var ta: TypedArray? = null
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.TitleView)
            leftDrawableId = ta.getResourceId(R.styleable.TitleView_titleView_left_drawable, R.mipmap.ic_arrow_back_black)
            leftTextId = ta.getResourceId(R.styleable.TitleView_titleView_left_text, R.string.back)
            middleTextId = ta.getResourceId(R.styleable.TitleView_titleView_title_text, R.string.login)
            leftTextColorId = ta.getColor(R.styleable.TitleView_titleView_left_text_color, resources.getColor(android.R.color.black))
            middleTextColorId = ta.getColor(R.styleable.TitleView_titleView_middle_text_color, resources.getColor(android.R.color.black))
            rightTextId = ta.getResourceId(R.styleable.TitleView_titleView_right_text, R.string.complete)
            rightTextColorId = ta.getColor(R.styleable.TitleView_titleView_right_text_color, resources.getColor(android.R.color.black))
            val type = ta.getInt(R.styleable.TitleView_titleView_visible_type, COMPOSE_LEFT_MIDDLEL_FLAG)
            when (type) {
                1 -> {
                    flag = COMPOSE_LEFT_MIDDLEL_FLAG
                }
                2 -> {
                    flag = COMPOSE_ALL_FLAG
                }
                3 -> {
                    flag = FLAG_LEFT_VISIBLE
                }
            }


        } finally {
            ta?.recycle()
        }

        setFlag(flag)
        setLeftDrawableId(leftDrawableId)
        setLeftTextId(leftTextId)
        setLeftTextColorId(leftTextColorId)
        setMiddleTextId(middleTextId)
        setMiddleTextColorId(middleTextColorId)
        setRightTextId(rightTextId)
        setRightTextColorId(rightTextColorId)
    }

    fun setFlag(flag: Int) {
        this.flag = flag
        if (flag and FLAG_LEFT_VISIBLE != 0) {
            iv_left_icon.visibility = View.VISIBLE
            tv_left_text.visibility = View.VISIBLE
        } else {
            iv_left_icon.visibility = View.INVISIBLE
            tv_left_text.visibility = View.INVISIBLE
        }

        if (flag and FLAG_MIDDLE_VISIBLE != 0) {
            tv_title.visibility = View.VISIBLE
        } else {
            tv_title.visibility = View.INVISIBLE
        }

        if (flag and FLAG_RIGHT_VISIBLE != 0) {
            right_container.visibility = View.VISIBLE
        } else {
            right_container.visibility = View.INVISIBLE
        }
    }

    fun setLeftDrawableId(leftDrawableId: Int) {
        this.leftDrawableId = leftDrawableId
        iv_left_icon.setImageResource(leftDrawableId)
    }

    fun setLeftTextId(leftTextId: Int) {
        this.leftTextId = leftTextId
        tv_left_text.visibility = View.VISIBLE
        tv_left_text.setText(leftTextId)
    }

    fun setLeftTextColorId(leftTextColorId: Int) {
        this.leftTextColorId = leftTextColorId
        tv_left_text.setTextColor(leftTextColorId)
    }

    fun setRightTextId(rightTextId: Int) {
        this.rightTextId = rightTextId
        tv_right_text.visibility = View.VISIBLE
        tv_right_text.setText(rightTextId)
    }

    fun setRightTextColorId(rightTextColorId: Int) {
        this.rightTextColorId = rightTextColorId
        tv_right_text.setTextColor(rightTextColorId)
    }

    fun setMiddleTextId(middleTextId: Int) {
        this.middleTextId = middleTextId
        tv_title.visibility = View.VISIBLE
        tv_title.setText(middleTextId)
    }

    fun setMiddleTextColorId(middleTextColorId: Int) {
        this.middleTextColorId = middleTextColorId
        tv_title.setTextColor(middleTextColorId)
    }

    fun setLeftAction(onClickListener: OnClickListener?) {
        left_container.setOnClickListener(onClickListener)
    }

    fun setRightAction(onClickListener: OnClickListener?) {
        right_container.setOnClickListener(onClickListener)
    }
}
