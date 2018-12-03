package com.jacky.beedee.logic.thridLogin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jacky.beedee.logic.thridLogin.ThirdLoginHelper.mTencent
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONObject
import com.tencent.open.utils.HttpUtils.requestAsync



/**
 * 2018/12/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class LoginDelegateActivity : Activity() {
    private val loginListener: IUiListener = object : BaseUiListener() {
        override fun doComplete(values: JSONObject) {
            val listener = ThirdLoginHelper.listener
            listener?.onSuccess(AuthResult(values.getString("access_token"), Platforms.QQ))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThirdLoginHelper.getTencent().logout(this)
        ThirdLoginHelper.getTencent().loginServerSide(this, "all", loginListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        ThirdLoginHelper.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener)
        }
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
        }
    }
}