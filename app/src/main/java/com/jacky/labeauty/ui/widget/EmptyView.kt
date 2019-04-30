package com.jacky.labeauty.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

import com.jacky.labeauty.R
import kotlinx.android.synthetic.main.layout_emptyview.view.*

class EmptyView @JvmOverloads constructor(context: Context, id: Int, attrs: AttributeSet? = null, defStyleAttr: Int = 0, val text: Int = -1) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        addView(LayoutInflater.from(context).inflate(R.layout.layout_emptyview, this, false))
        imageView.setImageResource(id)
        if (text > 0) {
            tvDesc.setText(text)
        } else {
            tvDesc.visibility = View.GONE
        }
    }
}
