package com.jacky.sub.beedee.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout

import com.jacky.sub.beedee.R
import kotlinx.android.synthetic.main.layout_emptyview.view.*

class EmptyView @JvmOverloads constructor(context: Context, id: Int, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        addView(LayoutInflater.from(context).inflate(R.layout.layout_emptyview, this, false))
        imageView.setImageResource(id)
    }
}
