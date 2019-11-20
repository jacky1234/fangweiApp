package com.jacky.labeauty.logic.network.interest;

import com.jacky.labeauty.support.log.Logger;
import com.jacky.labeauty.ui.dialog.DialogTipsHelper;
import com.jacky.labeauty.ui.inner.arch.BaseActivity;
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
            tipDialog = DialogTipsHelper.INSTANCE.createDefaultLoading(activity);
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
            try {
                tipDialog.dismiss();
            } catch (Exception e) {
                Logger.e(e);
            }
        }
    }
}