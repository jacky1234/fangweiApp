package com.jacky.beedee.ui

import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.support.ext.toast
import com.jacky.beedee.support.util.Strings
import com.jacky.beedee.support.util.regex.RegexUtils
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.android.ActivityEvent
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        titleView.setLeftAction(View.OnClickListener { finish() })

        val login = tv_login
        RxView.clicks(login).throttleFirst(1, TimeUnit.SECONDS)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe {
                    val phone = et_phone.text.toString()
                    if (Strings.isNullOrEmpty(phone)) {
                        toast("请输入手机号")
                        return@subscribe
                    }

                    if (!RegexUtils.isMobileSimple(phone)) {
                        toast("请输入正确的手机号")
                        return@subscribe
                    }

                    val pwd = et_pwd.text.toString()
                    if (!Strings.isNullOrEmpty(pwd)) {
                        toast("请输入密码")
                        return@subscribe
                    }

                    TODO("login")
                }
    }
}