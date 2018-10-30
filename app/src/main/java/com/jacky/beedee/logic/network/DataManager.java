package com.jacky.beedee.logic.network;

import android.support.annotation.NonNull;

import com.jacky.beedee.logic.entity.request.ReigsterRequest;
import com.jacky.beedee.logic.entity.response.HttpResponseSource;
import com.jacky.beedee.logic.entity.response.RegisterResponse;
import com.king.kotlinmvp.rx.scheduler.SchedulerUtils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;

/**
 * 2018/10/30.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 * 请求接口
 */
public class DataManager {
    private ApiService apiService;

    private DataManager() {
        apiService = RetrofitManager.Companion.get().getService();
    }


    public Observable<RegisterResponse> register(@NotNull String phone, @NonNull String code) {
        return apiService.register(new ReigsterRequest(phone, code))
                .compose(SchedulerUtils.INSTANCE.ioToMain())
                .compose(ResponseTransformer.handleResult());
    }


    public Observable<HttpResponseSource> sendCode(@NotNull String phone) {
        return apiService.sendCode(phone)
                .compose(SchedulerUtils.INSTANCE.ioToMain());
    }


    public static DataManager get() {
        return InstanceHolder.INSTANCE;
    }

    private static final class InstanceHolder {
        private static final DataManager INSTANCE = new DataManager();
    }


}
