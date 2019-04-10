package com.jacky.labeauty.logic.network.transformer;

import com.jacky.labeauty.logic.network.interest.LoadingInterest;

import io.reactivex.ObservableTransformer;

/**
 * 2018/11/2.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class LoadingTransformer {
    public static <T> ObservableTransformer<T, T> loading(boolean loading) {
        return upstream -> {
            if (loading){
                return upstream.compose(InterestTransformer.interest(new LoadingInterest()));
            }

            return upstream;
        };
    }
}
