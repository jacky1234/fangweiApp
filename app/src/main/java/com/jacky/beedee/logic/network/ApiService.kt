package com.jacky.beedee.logic.network

import com.jacky.beedee.logic.entity.request.ReigsterRequest
import com.jacky.beedee.logic.entity.response.HttpResponse
import com.jacky.beedee.logic.entity.response.HttpResponseSource
import com.jacky.beedee.logic.entity.response.RegisterResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    fun login(@Field("mobile") mobile: String
              , @Field("password") password: String
    ): Observable<HttpResponse<RegisterResponse>>

    @POST("send_sms_code")
    @FormUrlEncoded
    fun sendCode(@Field("mobile") mobile: String): Observable<HttpResponseSource>

}