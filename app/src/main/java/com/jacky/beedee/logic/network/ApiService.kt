package com.jacky.beedee.logic.network

import com.jacky.beedee.logic.entity.User
import com.jacky.beedee.logic.entity.request.LoginRequest
import com.jacky.beedee.logic.entity.request.ReigsterRequest
import com.jacky.beedee.logic.entity.request.UpdateUserRequest
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
    fun register(@Body registerRequest: ReigsterRequest): Observable<HttpResponse<RegisterResponse>>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Observable<HttpResponse<LoginResponse>>

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
    fun collectList(@Query("targetType") targetType: String,
                    @Query("page") page: Int,
                    @Query("size") size: Int): Observable<HttpResponse<FavoriteResponse>>
}