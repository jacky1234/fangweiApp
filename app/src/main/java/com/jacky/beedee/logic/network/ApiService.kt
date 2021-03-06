package com.jacky.beedee.logic.network

import com.jacky.beedee.logic.entity.module.Banner
import com.jacky.beedee.logic.entity.module.Category
import com.jacky.beedee.logic.entity.module.Good
import com.jacky.beedee.logic.entity.module.User
import com.jacky.beedee.logic.entity.request.*
import com.jacky.beedee.logic.entity.response.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * <pre>
 *     author : Wp
 *     e-mail : 18141924293@163.com
 *     time   : 2018/09/30
 *     desc   : Api接口
 *     version: 1.0
 * </pre>
 */
interface ApiService {

    @Multipart
    @POST("oss/upload")
    fun uploadFile(@Part file: MultipartBody.Part): Observable<HttpResponse<UploadFileResponse>>

    @POST("login")
    fun register(@Body registerRequest: ReigsterRequest): Observable<HttpResponse<User>>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Observable<HttpResponse<User>>

    @POST("login_wechat")
    fun loginWX(@Body request: WXLoginRequest): Observable<HttpResponse<User>>

    @POST("login_qq")
    fun loginQQ(@Body request: QQLoginRequest): Observable<HttpResponse<User>>


    @POST("login_weibo")
    fun loginWB(@Body request: WBLoginRequest): Observable<HttpResponse<User>>

    @POST("send_sms_code")
    fun sendCode(@Body map: Map<String, String>): Observable<HttpResponseSource>

    @POST("forgot_password")
    fun forgetPwd(@Body map: Map<String, String>): Observable<HttpResponseSource>

    @POST("user/update")
    fun updateUserInfo(@Body updateUserRequest: UpdateUserRequest): Observable<HttpResponse<User>>

    @POST("change_mobile")
    fun changeMobile(@Body map: Map<String, String>): Observable<HttpResponse<User>>

    @POST("logout")
    fun logout(): Observable<HttpResponseSource>

    @GET("collect/list")
    fun requestCollectList(@Query("targetType") targetType: String,
                           @Query("page") page: Int,
                           @Query("size") size: Int): Observable<HttpResponse<FavoriteResponse>>

    @GET("banner/list")
    fun requestBannerList(): Observable<HttpListResponse<Banner>>

    //查询热门穿搭（首页“人气穿搭”和人气穿搭页“搭配推荐”）
    @GET("outfit/list_hot")
    fun requestOutfitHot(@Query("page") page: Int): Observable<HttpResponse<ListGoodResponse>>

    //查询最新穿搭
    @GET("outfit/list_new")
    fun requestOutfitNew(): Observable<HttpResponse<ListGoodResponse>>

    @GET("outfit/search")
    fun searchOutfit(): Observable<HttpResponse<ListGoodResponse>>

    //查询穿搭详情
    @GET("outfit/get")
    fun requestOutfitDetail(@Query("outfitId") outfitId: String): Observable<HttpResponse<Good>>

    //查询商品详情
    @GET("goods/get")
    fun requestGoodDetail(@Query("goodsId") goodsId: String): Observable<HttpResponse<Good>>


    @GET("goods/list_hot")
    fun requestHotGoods(@Query("page") page: Int): Observable<HttpResponse<ListGoodResponse>>

    @GET("design/list_hot")
    fun requestHotDesignVideo(@Query("page") page: Int): Observable<HttpResponse<HotVideoResponse>>

    @POST("collect")
    fun collectGood(@Body request: CollectRequest): Observable<HttpResponse<CollectResponse>>

    @POST("uncollect")
    fun uncollectGood(@Body request: CollectRequest): Observable<HttpResponseSource>

    @GET("category/list")
    fun requestFirstCategory(): Observable<HttpListResponse<Category>>

    @GET("goods/search")
    fun requestSearchGood(@Query("keyword") keyword: String): Observable<HttpResponse<ListGoodResponse>>

    @GET("goods/group_by_category")
    fun requestGroupByCatalogue(@Query("categoryId") categoryId: String): Observable<HttpListResponse<SecondCategoryResponse>>

    @POST("feedback/post")
    fun feedbackProblem(@Body request: FeedbackRequest): Observable<HttpResponseSource>

    @GET("goods/get_search_term")
    fun getSearchKeyword(): Observable<HttpResponse<KeywordResponse>>
}