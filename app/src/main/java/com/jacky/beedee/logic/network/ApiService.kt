package com.jacky.beedee.logic.network

import com.jacky.beedee.logic.entity.base.HttpResponse
import com.jacky.beedee.logic.entity.response.RegisterResponse
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

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

    /**
     * 获取图片列表
     */
    @POST("/login")
    fun reigster(@Query("url") url: String): Observable<HttpResponse<RegisterResponse>>

}