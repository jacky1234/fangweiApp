package com.jacky.beedee.support.core;

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
