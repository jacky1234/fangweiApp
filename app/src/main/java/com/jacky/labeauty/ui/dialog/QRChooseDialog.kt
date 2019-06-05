package com.jacky.labeauty.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.jacky.labeauty.R
import kotlinx.android.synthetic.main.dialog_qr_choose.*

class QRChooseDialog(context: Context, val listener: OnChooseListener?) : AlertDialog(context, R.style.dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_qr_choose)

        setCanceledOnTouchOutside(true)
        setCancelable(true)

        tv_source.setOnClickListener {
            listener?.onSource()
            dismiss()
        }

        tv_defake.setOnClickListener {
            listener?.onDefake()
            dismiss()
        }
    }

    interface OnChooseListener {
        fun onDefake()
        fun onSource()
    }
}