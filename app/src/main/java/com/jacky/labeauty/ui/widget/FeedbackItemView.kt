package com.jacky.labeauty.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.AndroidUtil
import kotlinx.android.synthetic.main.item_feedback_problem.view.*

class FeedbackItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private var titleId = 0
    private var contentId = 0

    init {
        addView(LayoutInflater.from(context).inflate(R.layout.item_feedback_problem, this, false))
        var ta: TypedArray? = null
        var checked = false
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.FeedbackItemView)
            titleId = ta.getResourceId(R.styleable.FeedbackItemView_feedback_itemview_title, R.string.function_exception)
            contentId = ta.getResourceId(R.styleable.FeedbackItemView_feedback_itemview_content, R.string.function_issue)
            checked = ta.getBoolean(R.styleable.FeedbackItemView_feedback_itemview_checked, false)
        } finally {
            ta?.recycle()
        }

        if (titleId != 0) {
            tv_title.setText(titleId)
        }

        if (contentId != 0) {
            tv_content.setText(contentId)
        }

        cb_checked.isChecked = checked
    }

    fun setChecked(checked: Boolean) {
        cb_checked.isChecked = checked
    }

    fun toggleChecked() {
        cb_checked.isChecked = !cb_checked.isChecked
    }

    fun isChecked() = cb_checked.isChecked

    fun setCheckListener(listener: OnCheckListener?) {
        cb_checked.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                listener?.onChecked(this@FeedbackItemView)
            }
        }
    }

    interface OnCheckListener {
        fun onChecked(view: FeedbackItemView)
    }

    fun getCategory(): CharSequence {
        if (titleId != 0) {
            return AndroidUtil.getString(titleId)
        }
        return ""
    }
}
