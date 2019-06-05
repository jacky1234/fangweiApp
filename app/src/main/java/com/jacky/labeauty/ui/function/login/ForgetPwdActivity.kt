package com.jacky.labeauty.ui.function.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.MiscFacade
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.ext.toast
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.Checker
import com.jacky.labeauty.support.util.regex.RegexUtils
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_forget_pwd.*

class ForgetPwdActivity : BaseActivity() {
    companion object {
        @JvmField
        val KEY_FORGET_PWD_MOBILE = "KEY_FORGET_PWD_MOBILE"

        @JvmStatic
        fun start(activity: BaseActivity, mobile: String) {
            val intent = Intent(activity, ForgetPwdActivity::class.java)
            intent.putExtra(KEY_FORGET_PWD_MOBILE, mobile)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd)

        val mobile = intent.getStringExtra(KEY_FORGET_PWD_MOBILE)
        et_phone.setText(mobile)

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
            val phone = et_phone.text.toString()
            if (!RegexUtils.isMobileSimple(phone)) {
                toast(R.string.mobile_number_wrong)
                return@clickWithTrigger
            }

            RxView.enabled(btnGainCode).accept(false)
            RequestHelper.get().sendCode(phone).subscribe {
                AndroidUtil.toast(R.string.send_success)
                trigVerifyCode(btnGainCode)
            }
        }

        tv_confirm.clickWithTrigger {
            if (Checker.check(et_phone, AndroidUtil.getString(R.string.please_input_phone_number)) &&
                    Checker.checkMobile(et_phone) &&
                    Checker.check(et_code, AndroidUtil.getString(R.string.please_input_sms_code)) &&
                    Checker.check(et_pwd, AndroidUtil.getString(R.string.please_input_pwd)) &&
                    Checker.check(et_confirm_pwd, AndroidUtil.getString(R.string.please_confirm_pwd)) &&
                    Checker.equalsContent(et_pwd, et_confirm_pwd, AndroidUtil.getString(R.string.twice_pwd_difference))) {
                RequestHelper.get().forgetPwd(et_phone.text.toString(), et_code.text.toString(), et_pwd.text.toString())
                        .subscribe {
                            toast(R.string.update_success)
                            finish()
                        }
            }
        }
    }

    private fun trigVerifyCode(btnGainCode: Button) {
        MiscFacade.get().registerCodeListenerAndTrigger(codeObserver)
    }

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
}