package com.jacky.labeauty.ui.dialog;

import android.app.Activity;

import com.jacky.labeauty.R;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DialogHelper {
    private static int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    public static QMUIDialog createSimpleConfirmDialog(@NotNull Activity activity, @NotNull CharSequence detail, @Nullable QMUIDialogAction.ActionListener positiveAction) {
        return new QMUIDialog.MessageDialogBuilder(activity)
                .setMessage(detail)
                .addAction(R.string.cancel, (dialog, index) -> dialog.dismiss())
                .addAction(R.string.confirm, (dialog, index) -> {
                    dialog.dismiss();
                    if (positiveAction != null) {
                        positiveAction.onClick(dialog, index);
                    }
                })
                .create(mCurrentDialogStyle);
    }

}
