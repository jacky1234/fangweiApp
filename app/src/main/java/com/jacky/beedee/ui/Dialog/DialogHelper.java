package com.jacky.beedee.ui.Dialog;

import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * 2018/10/31.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class DialogHelper {
    public static QMUITipDialog createSuccess(Context context) {
        return createSuccess(context, "发送成功");
    }

    public static QMUITipDialog createSuccess(Context context, String s) {
        return new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(s)
                .create();
    }
}
