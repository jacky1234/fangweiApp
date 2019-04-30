package com.jacky.labeauty.ui.dialog

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.jacky.labeauty.R

class FeedbackSuccessDialog(mContext: Context) : AlertDialog(mContext, R.style.dialog) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_feedback_success)
        initView()
    }

    private fun initView() {
        // 点击空白区域不消失
        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }

    override fun show() {
        super.show()
//        val defaultDisplay = window.windowManager.defaultDisplay
//        val attributes = window.attributes
//        attributes.width = (defaultDisplay.width * WIDTH_SCALE).toInt()
//        attributes.height = (defaultDisplay.height * HEIGHT_SCALE).toInt()
//        window.attributes = attributes
    }
}