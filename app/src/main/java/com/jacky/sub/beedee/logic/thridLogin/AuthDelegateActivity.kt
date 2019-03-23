package com.jacky.sub.beedee.logic.thridLogin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jacky.sub.beedee.support.Starter
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.auth.WbConnectErrorMessage
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONObject


/**
 * 2018/12/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class AuthDelegateActivity : Activity() {
    private var flag: Int = FLAG_LOGIN_QQ
    private var mSsoHandler: SsoHandler? = null

    companion object {
        const val KEY_FLAG = "KEY_FLAG"

        const val FLAG_LOGIN_QQ = 1
        const val FLAG_LOGIN_WB = 2

        @JvmStatic
        fun launch(flag: Int) {
            val intent = Intent(Starter.getContext(), AuthDelegateActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(KEY_FLAG, flag)
            Starter.getContext().startActivity(intent)
        }
    }

    private val loginListener: IUiListener = object : BaseUiListener() {
        override fun doComplete(values: JSONObject) {
            val listener = ThirdLoginHelper.listener
            listener?.onSuccess(AuthResult(values.getString("access_token"), Platforms.QQ))
            finish()
        }
    }

    private val wbListener: WbAuthListener = object : WbAuthListener {
        override fun onSuccess(token: Oauth2AccessToken) {
            if (token.isSessionValid) {
                AccessTokenKeeper.writeAccessToken(Starter.getContext(), token)
                val listener = ThirdLoginHelper.listener
                listener?.onSuccess(AuthResult(token.token, Platforms.WEIBO))
                finish()
            }
        }

        override fun onFailure(errorMessage: WbConnectErrorMessage) {
            val listener = ThirdLoginHelper.listener
            listener?.onError(AuthThrowable(errorMessage.errorMessage))
            finish()
        }

        override fun cancel() {
            val listener = ThirdLoginHelper.listener
            listener?.onCancel()
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flag = intent.getIntExtra(KEY_FLAG, FLAG_LOGIN_QQ)

        when (flag) {
            FLAG_LOGIN_QQ -> {
                ThirdLoginHelper.getTencent().logout(this)
                ThirdLoginHelper.getTencent().login(this, "all", loginListener)
            }
            FLAG_LOGIN_WB -> {
                mSsoHandler = SsoHandler(this)
                mSsoHandler?.authorizeClientSso(wbListener)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ThirdLoginHelper.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener)
        }

        mSsoHandler?.authorizeCallBack(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    private open inner class BaseUiListener : IUiListener {

        override fun onComplete(response: Any?) {
            val listener = ThirdLoginHelper.listener
            if (null == response) {
                listener?.onError(AuthThrowable("返回为空, 登录失败"))
                finish()
                return
            }
            val jsonResponse = response as JSONObject?
            if (null != jsonResponse && jsonResponse.length() == 0) {
                listener?.onError(AuthThrowable("返回为空, 登录失败"))
                finish()
                return
            }

            doComplete(response)
        }

        protected open fun doComplete(values: JSONObject) {

        }

        override fun onError(e: UiError) {
            val listener = ThirdLoginHelper.listener
            listener?.onError(AuthThrowable(e.errorDetail))
            finish()
        }

        override fun onCancel() {
            val listener = ThirdLoginHelper.listener
            listener?.onCancel()
            finish()
        }
    }
}