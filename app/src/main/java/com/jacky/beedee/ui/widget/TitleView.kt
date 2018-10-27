package com.jacky.beedee.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
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
    private lateinit var iv_left_icon: ImageView
    private lateinit var tv_left_text: TextView
    private lateinit var tv_title: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.title_view, null)
        addView(view)

        iv_left_icon = view.findViewById(R.id.iv_left_icon)
        tv_left_text = view.findViewById(R.id.tv_left_text)
        tv_title = view.findViewById(R.id.tv_title)
        
    }

}
