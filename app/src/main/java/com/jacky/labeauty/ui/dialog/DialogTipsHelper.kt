package com.jacky.labeauty.ui.dialog

import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.Gravity
import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.AndroidUtil
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import top.limuyang2.ldialog.LDialog
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder

/**
 * 2018/10/31.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 * dialog helper
 */
object DialogTipsHelper {

    @JvmOverloads
    fun createSuccess(context: Context, s: String = "发送成功"): QMUITipDialog {
        return QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(s)
                .create()
    }

    fun showSuccessAndDismiss(context: Context, s: String) {
        val success = createSuccess(context, s)
        AndroidUtil.runUI({ success.show() }, 1000)
    }

    fun createDefaultLoading(context: Context): QMUITipDialog {
        return createLoading(context, "正在加载")
    }

    fun createLoading(context: Context, s: String): QMUITipDialog {
        return QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(s)
                .create()
    }


    fun createOpenLuckyBoxDialog(activity: Activity): AlertDialog {
        // TODO: 2019/4/25  中奖 dialog
        val builder = android.support.v7.app.AlertDialog.Builder(activity)
        builder.setView(R.layout.dialog_open_lucky_box)
        builder.setTitle("Material Design Dialog")
        builder.setMessage("这是 android.support.v7.app.AlertDialog 中的样式")
        builder.setNegativeButton("取消", null)
        builder.setPositiveButton("确定", null)
        return builder.show()
    }

    fun showDiscountDetailDialog(fragmentManager: android.support.v4.app.FragmentManager) {
        LDialog.init(fragmentManager)
                .setLayoutRes(R.layout.layout_discount_detail)
//                .setBackgroundDrawableRes(R.drawable.shape_share_dialog_bg)
                .setGravity(Gravity.CENTER)
                .setWidthScale(0.95f)
                .setVerticalMargin(0.015f)
//                .setAnimStyle(R.style.LDialogBottomAnimation)
                .setViewHandlerListener(object : ViewHandlerListener() {
                    override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
//                        holder.setOnClickListener(R.id.cancelBtn, View.OnClickListener {
//                            dialog.dismiss()
                    }
                }).show()
    }

    fun createFeedbackDialog(context: Context): AlertDialog {
        return FeedbackSuccessDialog(context)
    }
}
