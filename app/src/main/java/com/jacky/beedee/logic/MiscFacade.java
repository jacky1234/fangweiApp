package com.jacky.beedee.logic;

import android.app.Activity;
import android.content.Intent;

import com.jacky.beedee.logic.entity.MySelf;
import com.jacky.beedee.logic.network.RequestHelper;
import com.jacky.beedee.ui.function.login.LoginActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MiscFacade {
    private static boolean isNeedToLogout = false;
    //验证码周期
    private static final int VERTIFY_SECOND = 60;
    private Long currentPassSecond;
    private Observable<Long> longObservable;

    private MiscFacade() {
        longObservable = Observable.interval(1, TimeUnit.SECONDS).take(VERTIFY_SECOND)
                .observeOn(AndroidSchedulers.mainThread());
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

    public boolean isVertifyCodeAvailable() {
        return currentPassSecond == null || currentPassSecond == VERTIFY_SECOND || currentPassSecond == VERTIFY_SECOND - 1;
    }

    public Observable<Long> vertifyCodeObservable() {
        return Observable.create(emitter -> {
            Consumer<Long> longConsumer = aLong -> {
                currentPassSecond = aLong;
                emitter.onNext(VERTIFY_SECOND - currentPassSecond);
            };

            longObservable.subscribe(longConsumer);

//            if (isVertifyCodeAvailable()) {
//                longObservable = Observable.interval(1, TimeUnit.SECONDS).take(VERTIFY_SECOND)
//                        .observeOn(AndroidSchedulers.mainThread());
//                longObservable.subscribe(longConsumer);
//            } else {
//            }
        });
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
