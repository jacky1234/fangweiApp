package com.jacky.beedee.logic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import com.jacky.beedee.logic.entity.module.MySelf;
import com.jacky.beedee.logic.network.RequestHelper;
import com.jacky.beedee.ui.function.login.LoginActivity;
import com.jacky.beedee.ui.inner.arch.BaseActivity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

public class MiscFacade {
    private static boolean isNeedToLogout = false;
    //验证码周期
    private static final int VERTIFY_SECOND = 60;
    private ConnectableObservable<Long> codeObservable;
    private Disposable vertifyCodeDispose;
    private Set<Observer<Long>> leftSecondObservers = Collections.synchronizedSet(new HashSet<>());
    private Runnable lastRunnable;

    public Runnable getLastRunnable() {
        return lastRunnable;
    }

    public void setLastRunnable(Runnable lastRunnable) {
        this.lastRunnable = lastRunnable;
    }

    private MiscFacade() {

    }

    @SuppressLint("CheckResult")
    private void resetVertifyCodeDisposeAndConnect() {
        codeObservable = Observable.interval(1, TimeUnit.SECONDS).take(VERTIFY_SECOND)
                .observeOn(AndroidSchedulers.mainThread()).publish();
        vertifyCodeDispose = codeObservable.connect();
        codeObservable.subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                publishOnSubscribe(d);
            }

            @Override
            public void onNext(Long aLong) {
                publishOnNext(aLong);
            }

            @Override
            public void onError(Throwable e) {
                publishOnError(e);
            }

            @Override
            public void onComplete() {
                publishOnComplete();
            }
        });
    }

    private void publishOnComplete() {
        for (Observer observer : leftSecondObservers) {
            observer.onComplete();
        }
    }

    private void publishOnError(Throwable e) {
        for (Observer<Long> observer : leftSecondObservers) {
            observer.onError(e);
        }
    }

    private void publishOnNext(Long aLong) {
        for (Observer<Long> observer : leftSecondObservers) {
            observer.onNext(VERTIFY_SECOND - aLong);
        }
    }

    private void publishOnSubscribe(Disposable d) {
        for (Observer<Long> observer : leftSecondObservers) {
            observer.onSubscribe(d);
        }
    }

    private static final class InstanceHolder {
        private static final MiscFacade INSTANCE = new MiscFacade();
    }

    public static MiscFacade get() {
        return MiscFacade.InstanceHolder.INSTANCE;
    }

    public void setIsNeedToLogout(boolean isNeedToLogout) {
        MiscFacade.isNeedToLogout = isNeedToLogout;
        if (BaseActivity.currentActivity != null) {
            loginOutFlag(BaseActivity.currentActivity, false);
        }
    }

    public boolean isVerifyCodeAvailable() {
        return vertifyCodeDispose == null || vertifyCodeDispose.isDisposed();
    }

    public void registerCodeListenerAndTrigger(Observer<Long> observer) {
        if (isVerifyCodeAvailable()) {
            resetVertifyCodeDisposeAndConnect();
        }
        leftSecondObservers.add(observer);
    }

    public boolean removeCodeListener(Observer<Long> observer) {
        return leftSecondObservers.remove(observer);
    }

    public void loginOutFlag(Activity activity, boolean force) {
        if (isNeedToLogout || force) {
            isNeedToLogout = false;
            if (MySelf.get().isLogined()) {
                RequestHelper.get().logout().subscribe();
            }
            MySelf.get().clear();
            activity.finishAffinity();
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }
}
