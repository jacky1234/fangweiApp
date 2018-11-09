package com.jacky.beedee.logic.network;

import android.support.annotation.NonNull;

import com.jacky.beedee.logic.entity.module.Banner;
import com.jacky.beedee.logic.entity.module.Good;
import com.jacky.beedee.logic.entity.module.User;
import com.jacky.beedee.logic.entity.request.CollectRequest;
import com.jacky.beedee.logic.entity.request.LoginRequest;
import com.jacky.beedee.logic.entity.request.ReigsterRequest;
import com.jacky.beedee.logic.entity.request.UpdateUserRequest;
import com.jacky.beedee.logic.entity.response.CollectResponse;
import com.jacky.beedee.logic.entity.response.FavoriteResponse;
import com.jacky.beedee.logic.entity.response.HotVideoResponse;
import com.jacky.beedee.logic.entity.response.HttpResponseSource;
import com.jacky.beedee.logic.entity.response.ListGoodResponse;
import com.jacky.beedee.logic.entity.response.LoginResponse;
import com.jacky.beedee.logic.entity.response.RegisterResponse;
import com.jacky.beedee.logic.entity.response.UploadFileResponse;
import com.jacky.beedee.logic.network.transformer.BooleanTransformer;
import com.jacky.beedee.logic.network.transformer.HttpListResponseTransformer;
import com.jacky.beedee.logic.network.transformer.HttpResponseTransformer;
import com.king.kotlinmvp.rx.scheduler.SchedulerUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Observable<UploadFileResponse> uploadFile(@NotNull File file) {
        return apiService.uploadFile(ParamCreator.prepareFilePart("file", file))
                .compose(HttpResponseTransformer.handleResult(true));
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

    public Observable<Boolean> forgetPwd(String mobile, String code, String newPwd) {
        Map<String, String> map = new HashMap<>(3);
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("newPassword", newPwd);
        return apiService.forgetPwd(map)
                .compose(BooleanTransformer.handleResult(true));
    }

    public Observable<User> updateUserInfo(@NotNull UpdateUserRequest user) {
        return apiService.updateUserInfo(user)
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<User> updateMobile(@NotNull String mobile, @NotNull String code) {
        Map<String, String> map = new HashMap<>(2);
        map.put("newMobile", mobile);
        map.put("code", code);

        return apiService.changeMobile(map)
                .compose(HttpResponseTransformer.handleResult(true));
    }

    //收藏
    public Observable<FavoriteResponse> requestCollectList(@NotNull String targetType, int page) {
        return apiService.requestCollectList(targetType, page, ParamCreator.PAGE_SIZE)
                .compose(HttpResponseTransformer.handleResult(false));
    }

    //横幅
    public Observable<List<Banner>> requestBannerList() {
        return apiService.requestBannerList()
                .compose(HttpListResponseTransformer.handleResult(false));
    }

    //穿搭
    public Observable<ListGoodResponse> requestOutfitHot() {
        return apiService.requestOutfitHot()
                .compose(HttpResponseTransformer.handleResult(false));
    }

    public Observable<Good> requestOutfitDetail(String outfitId) {
        return apiService.requestOutfitDetail(outfitId)
                .compose(HttpResponseTransformer.handleResult(false));
    }

    //热门商品
    public Observable<ListGoodResponse> requestHotGoods() {
        return apiService.requestHotGoods()
                .compose(HttpResponseTransformer.handleResult(false));
    }

    public Observable<HotVideoResponse> requestDesignVideo() {
        return apiService.requestHotDesignVideo()
                .compose(HttpResponseTransformer.handleResult(false));
    }

    public Observable<CollectResponse> collectOutFit(String targetId) {
        CollectRequest request = new CollectRequest();
        request.setTargetId(targetId);
        request.setTargetType("OUTFIT");

        return apiService.collectGood(request)
                .compose(HttpResponseTransformer.handleResult(true));
    }


    public Observable<Boolean> uncollectOutFit(String targetId) {
        CollectRequest request = new CollectRequest();
        request.setTargetId(targetId);
        request.setTargetType("OUTFIT");

        return apiService.uncollectGood(request)
                .compose(BooleanTransformer.handleResult(true));
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
