package com.jacky.beedee.logic.network.interest;

import com.jacky.beedee.ui.Dialog.DialogTipsHelper;
import com.jacky.beedee.ui.inner.arch.BaseActivity;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * 2018/11/2.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class LoadingInterest implements Interest {
    private BaseActivity activity;
    private QMUITipDialog tipDialog;

    @Override
    public void onStart() {
        activity = BaseActivity.currentActivity;
        if (activity != null) {
            tipDialog = DialogTipsHelper.createDefaultLoading(activity);
            tipDialog.show();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        onEnd();
    }

    @Override
    public void onEnd() {
        if (activity != null && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
    }
}
