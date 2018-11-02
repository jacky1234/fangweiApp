package com.jacky.beedee.logic.network;

import android.support.annotation.NonNull;

import com.jacky.beedee.logic.entity.User;
import com.jacky.beedee.logic.entity.request.LoginRequest;
import com.jacky.beedee.logic.entity.request.Mobile;
import com.jacky.beedee.logic.entity.request.ReigsterRequest;
import com.jacky.beedee.logic.entity.request.UserRequest;
import com.jacky.beedee.logic.entity.response.HttpResponseSource;
import com.jacky.beedee.logic.entity.response.LoginResponse;
import com.jacky.beedee.logic.entity.response.RegisterResponse;
import com.jacky.beedee.logic.network.transformer.BooleanTransformer;
import com.jacky.beedee.logic.network.transformer.HttpResponseTransformer;
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
public class RequestHelper {
    private ApiService apiService;

    private RequestHelper() {
        apiService = RetrofitManager.Companion.get().getService();
    }


    public Observable<RegisterResponse> register(@NotNull String phone, @NonNull String code) {
        return apiService.register(new ReigsterRequest(phone, code))
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<LoginResponse> login(@NotNull String phone, @NonNull String pwd) {
        return apiService.login(new LoginRequest(phone, pwd))
                .compose(HttpResponseTransformer.handleResult(true));
    }


    public Observable<Boolean> sendCode(@NotNull String s) {
        Mobile mobile = new Mobile();
        mobile.setMobile(s);
        return apiService.sendCode(mobile)
                .compose(BooleanTransformer.handleResult(true));
    }

    public Observable<User> completeUserInfo(@NotNull UserRequest user) {
        return apiService.completeUserInfo(user)
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<HttpResponseSource> logout() {
        return apiService.logout().compose(SchedulerUtils.INSTANCE.ioToMain());
    }


    public static RequestHelper get() {
        return InstanceHolder.INSTANCE;
    }

    private static final class InstanceHolder {
        private static final RequestHelper INSTANCE = new RequestHelper();
    }


}
