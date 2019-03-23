package com.jacky.sub.beedee.logic.network.interest;

/**
 * 2018/11/2.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public interface Interest {
    void onStart();

    void onError(Throwable throwable);

    void onEnd();
}
