package com.jacky.labeauty.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

import com.jacky.labeauty.R
import kotlinx.android.synthetic.main.layout_emptyview.view.*

class EmptyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        addView(LayoutInflater.from(context).inflate(R.layout.layout_emptyview, this, false))
    }

    fun setImageResource(id: Int): EmptyView {
        imageView.setImageResource(id)
        return this
    }

    fun setDescID(imageResId: Int): EmptyView {
        if (imageResId > 0) {
            tvDesc.setText(imageResId)
        } else {
            tvDesc.visibility = View.GONE
        }
        return this
    }
}
