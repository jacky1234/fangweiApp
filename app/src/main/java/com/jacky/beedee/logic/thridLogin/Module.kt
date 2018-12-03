package com.jacky.beedee.logic.thridLogin

/**
 * 2018/12/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */

//wx code
//qq access_token
//
data class AuthResult(val code: String, @Platforms.PLATFORM val platform: Int)

interface OnThirdAuthListener {
    fun onSuccess(result: AuthResult)
    fun onError(e: AuthThrowable)
}