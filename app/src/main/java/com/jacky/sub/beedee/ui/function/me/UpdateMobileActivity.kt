package com.jacky.sub.beedee.ui.function.me

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.jacky.sub.beedee.R
import com.jacky.sub.beedee.logic.MiscFacade
import com.jacky.sub.beedee.logic.entity.module.MySelf
import com.jacky.sub.beedee.logic.network.RequestHelper
import com.jacky.sub.beedee.support.ext.clickWithTrigger
import com.jacky.sub.beedee.support.ext.launch
import com.jacky.sub.beedee.support.util.AndroidUtil
import com.jacky.sub.beedee.support.util.Checker
import com.jacky.sub.beedee.ui.function.main.MainActivity
import com.jacky.sub.beedee.ui.inner.arch.BaseActivity
import com.jacky.sub.beedee.ui.widget.TitleView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_modify_mobile.*

/**
 * 2018/11/4.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class UpdateMobileActivity : BaseActivity() {

    private val codeObserver = object : Observer<Long> {
        override fun onSubscribe(d: Disposable) {

        }

        override fun onNext(t: Long) {
            RxTextView.text(btn_gain_code).accept("剩余" + t + "秒")
        }

        override fun onError(e: Throwable) {
        }

        override fun onComplete() {
            RxTextView.textRes(btn_gain_code).accept(R.string.get_vertify_code)
            RxView.enabled(btn_gain_code).accept(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_mobile)

        titleView.setFlag(TitleView.COMPOSE_LEFT_MIDDLEL_FLAG)
        titleView.setLeftAction(View.OnClickListener {
            finish()
        })

        val btnGainCode = btn_gain_code
        val verifyCodeAvailable = MiscFacade.get().isVerifyCodeAvailable
        if (!verifyCodeAvailable) {
            RxTextView.text(btnGainCode).accept(null)
        }
        RxView.enabled(btnGainCode).accept(verifyCodeAvailable)
        if (!verifyCodeAvailable) {
            trigVerifyCode(btnGainCode)
        }
        btnGainCode.clickWithTrigger {
            val phone = et_new_mobile.text.toString()
            if (Checker.check(et_new_mobile, "请输入手机号") &&
                    Checker.checkMobile(et_new_mobile)) {
                RxView.enabled(btnGainCode).accept(false)
                RequestHelper.get().sendCode(phone).subscribe {
                    AndroidUtil.toast("发送成功")
                    trigVerifyCode(btnGainCode)
                }
            }
        }

        tv_confirm.clickWithTrigger {
            if (Checker.check(et_new_mobile, "请输入手机号") &&
                    Checker.checkMobile(et_new_mobile) &&
                    Checker.check(et_code, "请输入验证码")) {
                RequestHelper.get().updateMobile(et_new_mobile.text.toString(), et_code.text.toString())
                        .compose(bindToDestroy())
                        .subscribe {
                            MySelf.get().saveFromUser(it)
                            AndroidUtil.toast(R.string.update_success)
                            launch<MainActivity>()
                        }
            }
        }
    }

    private fun trigVerifyCode(btnGainCode: Button) {
        MiscFacade.get().registerCodeListenerAndTrigger(codeObserver)
    }
}