package com.jacky.beedee.logic.network;

import android.support.annotation.NonNull;

import com.jacky.beedee.logic.entity.User;
import com.jacky.beedee.logic.entity.request.LoginRequest;
import com.jacky.beedee.logic.entity.request.ReigsterRequest;
import com.jacky.beedee.logic.entity.request.UserRequest;
import com.jacky.beedee.logic.entity.response.HttpResponseSource;
import com.jacky.beedee.logic.entity.response.LoginResponse;
import com.jacky.beedee.logic.entity.response.RegisterResponse;
import com.jacky.beedee.logic.entity.response.UploadFileResponse;
import com.jacky.beedee.logic.network.transformer.BooleanTransformer;
import com.jacky.beedee.logic.network.transformer.HttpResponseTransformer;
import com.king.kotlinmvp.rx.scheduler.SchedulerUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 2018/10/30.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 * 请求接口
 */
public class RequestHelper {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType TYPE_FILE
            = MediaType.parse("application/octet-stream");

    private ApiService apiService;

    private RequestHelper() {
        apiService = RetrofitManager.Companion.get().getService();
    }

    public Observable<UploadFileResponse> uploadFile(@NotNull File file) {
        Map<String, RequestBody> map = new HashMap<>(1);
        map.put("file", RequestBody.create(TYPE_FILE, file));

        return apiService.uploadFile(map);
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
        Map<String, String> map = new HashMap<>(1);
        map.put("mobile", s);
        return apiService.sendCode(map)
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
