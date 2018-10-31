package com.jacky.beedee.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.logic.network.exception.CustomException
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.ext.toast
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.support.util.Strings
import com.jacky.beedee.support.util.regex.RegexUtils
import com.jacky.beedee.ui.Dialog.DialogHelper
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.android.ActivityEvent
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity() {
    lateinit var rxPermissions: RxPermissions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
                .subscribe()

        checkLogin()
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
                        toast(R.string.mobile_number_wrong)
                        return@subscribe
                    }

                    val pwd = et_pwd.text.toString()
                    if (Strings.isNullOrEmpty(pwd)) {
                        toast("请输入密码")
                        return@subscribe
                    }

                    RequestHelper.get().login(phone, pwd)
                            .compose(bindUntilEvent(ActivityEvent.DESTROY))
                            .subscribe({
                                DialogHelper.createSuccess(this, "登录成功")
                                AndroidUtil.runUI({ this@LoginActivity.launch<MainActivity>() }, 100)
                            }, { CustomException.handleException(it) })
                }

        tv_register.setOnClickListener({
            LoginActivity@ this.launch<RegisterActivity>()
        })
    }

    private fun checkLogin(): Boolean {
        //TODO(check)
        return false
    }
}