package com.jacky.sub.beedee.logic.thridLogin

import com.jacky.sub.beedee.logic.Constant
import com.jacky.sub.beedee.support.Starter
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.auth.AuthInfo
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.Tencent

/**
 * 2018/12/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
object ThirdLoginHelper {
    private var api: IWXAPI? = null     //wx
    var listener: OnThirdAuthListener? = null
    private var mTencent: Tencent? = null       //qq

    private var hasInitWbSdk = false

    fun loginWx(listener: OnThirdAuthListener?) {
        this.listener = listener
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "none"
        getWxApi().sendReq(req)
    }

    fun loginQQ(listener: OnThirdAuthListener?) {
        this.listener = listener
        AuthDelegateActivity.launch(AuthDelegateActivity.FLAG_LOGIN_QQ)
    }

    fun loginWb(listener: OnThirdAuthListener?) {
        this.listener = listener
        if (!hasInitWbSdk) {
            hasInitWbSdk = true
            WbSdk.install(Starter.getContext(),
                    AuthInfo(Starter.getContext(), Constant.WB_APP_ID, Constant.WB_CALLBACK_URL, "all"))
        }

        val mWbAccessToken = AccessTokenKeeper.readAccessToken(Starter.getContext())
        if (mWbAccessToken!!.isSessionValid) {
            listener?.onSuccess(AuthResult(mWbAccessToken.token, Platforms.WEIBO))
        } else {
            AuthDelegateActivity.launch(AuthDelegateActivity.FLAG_LOGIN_WB)
        }
    }

    fun getWxApi(): IWXAPI {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(Starter.getContext(), Constant.WX_APP_ID, false)
            api!!.registerApp(Constant.WX_APP_ID)
        }

        return api!!
    }

    fun getTencent(): Tencent {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(Constant.QQ_APP_ID, Starter.getContext())
        }

        return mTencent!!
    }

    fun clear() {
        api = null
        mTencent = null
        listener = null
    }
}