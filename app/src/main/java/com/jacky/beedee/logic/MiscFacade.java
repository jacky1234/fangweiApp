package com.jacky.beedee.logic;

import android.app.Activity;
import android.content.Intent;

import com.jacky.beedee.logic.entity.MySelf;
import com.jacky.beedee.logic.network.RequestHelper;
import com.jacky.beedee.ui.function.login.LoginActivity;

public class MiscFacade {
    private static boolean isNeedToLogout = false;

    private MiscFacade() {
    }

    private static final class InstanceHolder {
        public static final MiscFacade INSTANCE = new MiscFacade();

        // Don't let anyone instantiate this class.
        private InstanceHolder() {
            // This constructor is intentionally empty.
        }
    }

    public static MiscFacade get() {
        return MiscFacade.InstanceHolder.INSTANCE;
    }

    public static void setIsNeedToLogout(boolean isNeedToLogout) {
        MiscFacade.isNeedToLogout = isNeedToLogout;
    }

    public void loginOutFlag(Activity activity, boolean force) {
        if (isNeedToLogout || force) {
            isNeedToLogout = false;
            RequestHelper.get().logout().subscribe();
            MySelf.get().clear();
            activity.finishAffinity();
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }
}
