package com.jacky.beedee.logic.thridLogin

import android.content.Intent
import com.jacky.beedee.logic.Constant
import com.jacky.beedee.support.Starter
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
    private var api: IWXAPI? = null
    var listener: OnThirdAuthListener? = null
    var mTencent: Tencent? = null


    fun loginWx(listener: OnThirdAuthListener?) {
        this.listener = listener
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "none"
        getWxApi().sendReq(req)
    }

    fun loginQQ(listener: OnThirdAuthListener?) {
        this.listener = listener
        val intent = Intent(Starter.getContext(), LoginDelegateActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Starter.getContext().startActivity(intent)
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
        listener = null
    }
}