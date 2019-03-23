package com.jacky.sub.beedee.logic.network.transformer;


import com.jacky.sub.beedee.logic.network.interest.Interest;
import com.jacky.sub.beedee.support.log.Logger;
import com.jacky.sub.beedee.support.util.AndroidUtil;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 2018/11/2.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class InterestTransformer {
    private boolean isStartExecute;
    private boolean isEndExecute;
    private boolean isErrorExecute;
    private Interest interest;

    private InterestTransformer(Interest interest) {
        this.interest = interest;
    }

    public static <T> ObservableTransformer<T, T> interest(Interest interest) {
        InterestTransformer instance = new InterestTransformer(interest);
        return upstream -> upstream.doOnNext(t -> Logger.i("doOnNext..."))
                .doOnComplete(() -> {
                    Logger.i("doOnComplete...");
                    instance.onEnd();
                }).doOnError(throwable -> {
                    Logger.i("doOnError...");
                    instance.onError(throwable);
                }).doOnSubscribe((Consumer<Disposable>) disposable -> {
                    Logger.i("doOnSubscribe...");
                    instance.onStart();
                }).doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Logger.i("doOnDispose...");
                    }
                });
    }

    private void onEnd() {
        if (!isEndExecute && interest != null) {
            isEndExecute = true;
            AndroidUtil.runUI(() -> {
                if (interest != null) {
                    interest.onEnd();
                }
            });
        }
    }

    private void onStart() {
        if (!isStartExecute && interest != null) {
            isStartExecute = true;
            AndroidUtil.runUI(() -> {
                if (interest != null) {
                    interest.onStart();
                }
            });
        }
    }

    private void onError(Throwable throwable) {
        if (!isErrorExecute && interest != null) {
            isErrorExecute = true;
            AndroidUtil.runUI(() -> {
                if (interest != null) {
                    interest.onError(throwable);
                }
            });
        }
    }
}
