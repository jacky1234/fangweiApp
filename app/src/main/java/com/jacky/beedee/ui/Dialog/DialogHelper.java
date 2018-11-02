package com.jacky.beedee.ui.Dialog;

import android.app.Activity;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DialogHelper {
    private static int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    public static QMUIDialog createSimpleConfirmDialog(@NotNull Activity activity, @NotNull String detail, @Nullable QMUIDialogAction.ActionListener positiveAction) {
        return new QMUIDialog.MessageDialogBuilder(activity)
                .setTitle("提示")
                .setMessage(detail)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    dialog.dismiss();
                    if (positiveAction != null) {
                        positiveAction.onClick(dialog, index);
                    }
                })
                .create(mCurrentDialogStyle);
    }

}
