package com.jacky.labeauty.logic.network;

import android.support.annotation.NonNull;

import com.jacky.labeauty.logic.Constant;
import com.jacky.labeauty.logic.entity.module.Address;
import com.jacky.labeauty.logic.entity.module.Banner;
import com.jacky.labeauty.logic.entity.module.Category;
import com.jacky.labeauty.logic.entity.module.Feedback;
import com.jacky.labeauty.logic.entity.module.Good;
import com.jacky.labeauty.logic.entity.module.GoodType;
import com.jacky.labeauty.logic.entity.module.IntegralRecorder;
import com.jacky.labeauty.logic.entity.module.Message;
import com.jacky.labeauty.logic.entity.module.MsgCount;
import com.jacky.labeauty.logic.entity.module.MyDiscount;
import com.jacky.labeauty.logic.entity.module.MyIntegral;
import com.jacky.labeauty.logic.entity.module.MySelf;
import com.jacky.labeauty.logic.entity.module.Sign;
import com.jacky.labeauty.logic.entity.module.User;
import com.jacky.labeauty.logic.entity.request.AddAddressRequest;
import com.jacky.labeauty.logic.entity.request.CollectRequest;
import com.jacky.labeauty.logic.entity.request.FeedbackRequest;
import com.jacky.labeauty.logic.entity.request.LoginRequest;
import com.jacky.labeauty.logic.entity.request.QQLoginRequest;
import com.jacky.labeauty.logic.entity.request.ReigsterRequest;
import com.jacky.labeauty.logic.entity.request.UpdateUserRequest;
import com.jacky.labeauty.logic.entity.request.WBLoginRequest;
import com.jacky.labeauty.logic.entity.request.WXLoginRequest;
import com.jacky.labeauty.logic.entity.response.CollectResponse;
import com.jacky.labeauty.logic.entity.response.FavoriteResponse;
import com.jacky.labeauty.logic.entity.response.HotVideoResponse;
import com.jacky.labeauty.logic.entity.response.HttpPageResponse;
import com.jacky.labeauty.logic.entity.response.HttpResponseSource;
import com.jacky.labeauty.logic.entity.response.KeywordResponse;
import com.jacky.labeauty.logic.entity.response.ListGoodResponse;
import com.jacky.labeauty.logic.entity.response.PrizeResponse;
import com.jacky.labeauty.logic.entity.response.SecondCategoryResponse;
import com.jacky.labeauty.logic.entity.response.UploadFileResponse;
import com.jacky.labeauty.logic.network.transformer.BooleanTransformer;
import com.jacky.labeauty.logic.network.transformer.HttpListResponseTransformer;
import com.jacky.labeauty.logic.network.transformer.HttpPageResponseTransformer;
import com.jacky.labeauty.logic.network.transformer.HttpResponseTransformer;
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

    public Observable<User> register(@NotNull String phone, @NonNull String code) {
        MySelf.get().setAuthorization(null);
        return apiService.register(new ReigsterRequest(phone, code))
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<User> login(@NotNull String phone, @NonNull String pwd) {
        MySelf.get().setAuthorization(null);
        return apiService.login(new LoginRequest(phone, pwd))
                .compose(HttpResponseTransformer.handleResult(true));
    }

    // 微信 OAuth2授权码
    public Observable<User> loginWX(@NotNull String code) {
        MySelf.get().setAuthorization(null);
        return apiService.loginWX(new WXLoginRequest(code))
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<User> loginQQ(@NotNull String accessToken) {
        MySelf.get().setAuthorization(null);
        return apiService.loginQQ(new QQLoginRequest(accessToken))
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<User> loginWB(@NotNull String accessToken) {
        MySelf.get().setAuthorization(null);
        return apiService.loginWB(new WBLoginRequest(accessToken))
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<Boolean> sendCode(@NotNull String s) {
        Map<String, String> map = new HashMap<>(1);
        map.put("mobile", s);
        return apiService.sendCode(map)
                .compose(BooleanTransformer.handleResult(true));
    }

    public Observable<Feedback> feedbackProblem(@NotNull FeedbackRequest request) {
        return apiService.feedbackAdvice(request)
                .compose(HttpResponseTransformer.handleResult(true));
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
    public Observable<FavoriteResponse> requestCollectList(@NotNull GoodType goodType, int page) {
        return apiService.requestCollectList(goodType.name(), page, ParamCreator.PAGE_SIZE)
                .compose(HttpResponseTransformer.handleResult(false));
    }

    //横幅
    public Observable<List<Banner>> requestBannerList() {
        return apiService.requestBannerList()
                .compose(HttpListResponseTransformer.handleResult(false));
    }

    //穿搭
    public Observable<ListGoodResponse> requestOutfitHot(int page) {
        return apiService.requestOutfitHot(page)
                .compose(HttpResponseTransformer.handleResult(false));
    }

    public Observable<Good> requestOutfitDetail(String outfitId) {
        return apiService.requestOutfitDetail(outfitId)
                .compose(HttpResponseTransformer.handleResult(false));
    }

    public Observable<Good> requestGoodDetail(String outfitId) {
        return apiService.requestGoodDetail(outfitId)
                .compose(HttpResponseTransformer.handleResult(true));
    }

    //热门商品
    public Observable<ListGoodResponse> requestHotGoods(int page) {
        return apiService.requestHotGoods(page)
                .compose(HttpResponseTransformer.handleResult(false));
    }

    public Observable<HotVideoResponse> requestDesignVideo(int page) {
        return apiService.requestHotDesignVideo(page)
                .compose(HttpResponseTransformer.handleResult(false));
    }

    public Observable<CollectResponse> collectItem(@NotNull GoodType type, String targetId) {
        CollectRequest request = new CollectRequest();
        request.setTargetId(targetId);
        request.setTargetType(type.name());

        return apiService.collectGood(request)
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<CollectResponse> uncollectItem(@NotNull GoodType type, String targetId) {
        CollectRequest request = new CollectRequest();
        request.setTargetId(targetId);
        request.setTargetType(type.name());

        return apiService.collectGood(request)
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<List<Category>> requestFirstSort() {
        return apiService.requestFirstCategory()
                .compose(HttpListResponseTransformer.handleResult(true));
    }

    public Observable<ListGoodResponse> requestSearchGood(String key) {
        return apiService.requestSearchGood(key)
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<List<SecondCategoryResponse>> requestGroupByCategroy(String categoryId) {
        return apiService.requestGroupByCatalogue(categoryId)
                .compose(HttpListResponseTransformer.handleResult(true));
    }

    public Observable<HttpResponseSource> logout() {
        return apiService.logout().compose(SchedulerUtils.INSTANCE.ioToMain());
    }

    public Observable<KeywordResponse> getSearchKeyword() {
        return apiService.getSearchKeyword()
                .compose(HttpResponseTransformer.handleResult(false));
    }

    public Observable<MyIntegral> requestMyIntegral() {
        return apiService.requestIntegral()
                .compose(HttpResponseTransformer.handleResult(false));
    }

    //优惠劵
    public Observable<HttpPageResponse<MyDiscount>> requestDiscounts(int page) {
        return apiService.requestDiscounts(page)
                .compose(HttpPageResponseTransformer.handPageResult(false));
    }

    //提货
    public Observable<Boolean> bindAddress(String prizeLogId, String addressId) {
        return apiService.bindAddress(prizeLogId, addressId)
                .compose(BooleanTransformer.handleResult(true));
    }

    //签到
    public Observable<Sign> requestSign() {
        return apiService.requestSign()
                .compose(HttpResponseTransformer.handleResult(true));
    }

    //增加地址
    public Observable<Address> addAddressRecorder(AddAddressRequest request) {
        return apiService.addAddressRecorder(request)
                .compose(HttpResponseTransformer.handleResult(true));
    }

    public Observable<Boolean> deleteAddress(String addressId) {
        return apiService.deleteAddress(addressId)
                .compose(BooleanTransformer.handleResult(true));
    }

    public Observable<Address> updateAddress(boolean loading, Address address) {
        return apiService.updateAddress(address)
                .compose(HttpResponseTransformer.handleResult(loading));
    }

    public Observable<HttpPageResponse<IntegralRecorder>> requestIntegrals(int page, String direction) {
        return apiService.requestIntegrals(direction, page, 10)
                .compose(HttpPageResponseTransformer.handPageResult(false));
    }

    public Observable<PrizeResponse> requestPrizes() {
        return apiService.requestPrizes()
                .compose(HttpResponseTransformer.handleResult(false));
    }

    public Observable<List<Address>> requestAddresses(boolean loading) {
        return apiService.requestAddresses(0, Constant.MAX_PAGE_SIZE)
                .compose(HttpListResponseTransformer.handleResult(loading));
    }

    public Observable<Boolean> noticeMsgRead() {
        return apiService.noticeMsgRead()
                .compose(BooleanTransformer.handleResult(false));
    }

    public Observable<MsgCount> requestMsgCount() {
        return apiService.requestMsgCount()
                .compose(HttpResponseTransformer.handleResult(false));
    }

    public Observable<HttpPageResponse<Message>> requestMessages(int page) {
        return apiService.requestMessages(page, Constant.NORMAL_PAGE_SIZE)
                .compose(HttpPageResponseTransformer.handPageResult(false));
    }

    public static RequestHelper get() {
        return InstanceHolder.INSTANCE;
    }

    private static final class InstanceHolder {
        private static final RequestHelper INSTANCE = new RequestHelper();
    }
}
