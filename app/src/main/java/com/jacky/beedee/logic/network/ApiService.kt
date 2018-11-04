package com.jacky.beedee.logic.network

import com.jacky.beedee.logic.entity.User
import com.jacky.beedee.logic.entity.request.LoginRequest
import com.jacky.beedee.logic.entity.request.ReigsterRequest
import com.jacky.beedee.logic.entity.request.UserRequest
import com.jacky.beedee.logic.entity.response.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

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
    fun uploadFile(@PartMap map: Map<String, RequestBody>): Observable<UploadFileResponse>

    @POST("login")
    fun register(@Body registerRequest: ReigsterRequest): Observable<HttpResponse<RegisterResponse>>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Observable<HttpResponse<LoginResponse>>

    @POST("send_sms_code")
    fun sendCode(@Body map: Map<String, String>): Observable<HttpResponseSource>

    @POST("user/update")
    fun completeUserInfo(@Body request: UserRequest): Observable<HttpResponse<User>>

    @POST("logout")
    fun logout(): Observable<HttpResponseSource>
}