package com.jacky.beedee.logic.network

import com.jacky.beedee.logic.entity.User
import com.jacky.beedee.logic.entity.request.LoginRequest
import com.jacky.beedee.logic.entity.request.Mobile
import com.jacky.beedee.logic.entity.request.ReigsterRequest
import com.jacky.beedee.logic.entity.request.UserRequest
import com.jacky.beedee.logic.entity.response.HttpResponse
import com.jacky.beedee.logic.entity.response.HttpResponseSource
import com.jacky.beedee.logic.entity.response.LoginResponse
import com.jacky.beedee.logic.entity.response.RegisterResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

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

    @POST("login")
    fun register(@Body registerRequest: ReigsterRequest): Observable<HttpResponse<RegisterResponse>>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Observable<HttpResponse<LoginResponse>>

    @POST("send_sms_code")
    fun sendCode(@Body mobile: Mobile): Observable<HttpResponseSource>

    @POST("user/update")
    fun completeUserInfo(@Body request: UserRequest): Observable<HttpResponse<User>>

    @POST("logout")
    fun logout(): Observable<HttpResponseSource>
}