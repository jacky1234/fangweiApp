package com.jacky.labeauty.ui.function.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.MySelf
import com.jacky.labeauty.logic.entity.request.UpdateUserRequest
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.launch
import com.jacky.labeauty.support.ext.toast
import com.jacky.labeauty.support.util.Strings
import com.jacky.labeauty.ui.function.main.MainActivity
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.android.ActivityEvent
import kotlinx.android.synthetic.main.activity_register_fill_info.*
import java.util.concurrent.TimeUnit

class RegisterFillInfoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_fill_info)
        val phone = intent.getStringExtra(KEY_PHONE)
        val code = intent.getStringExtra(KEY_CODE)

        titleView.setLeftAction(View.OnClickListener { finish() })

        val complete = tv_complete
        RxView.clicks(complete).throttleFirst(2, TimeUnit.SECONDS)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe {
                    val nickname = et_nickname.text.toString()
                    if (Strings.isNullOrEmpty(nickname)) {
                        toast(R.string.please_input_nickname)
                        return@subscribe
                    }

                    val pwd = et_pwd.text.toString()
                    if (Strings.isNullOrEmpty(pwd)) {
                        toast(R.string.please_input_pwd)
                        return@subscribe
                    }
                    if (Strings.isNullOrEmpty(et_confirm_pwd.text.toString())) {
                        toast(R.string.please_confirm_pwd)
                        return@subscribe
                    }

                    if (pwd != et_confirm_pwd.text.toString()) {
                        toast(R.string.twice_pwd_difference)
                        return@subscribe
                    }
                    val request = UpdateUserRequest()
                    request.nickName = nickname
                    request.gender = if (rb_boy.isChecked) "MALE" else "FEMALE"
                    request.password = pwd
                    RequestHelper.get().updateUserInfo(request)
                            .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                            .subscribe {
                                MySelf.get().saveFromUser(it)
                                finishAffinity()
                                this@RegisterFillInfoActivity.launch<MainActivity>()
                            }

                }
    }

    companion object {
        private const val KEY_PHONE = "KEY_PHONE"
        private const val KEY_CODE = "KEY_CODE"

        @JvmStatic
        fun launch(activity: BaseActivity, phone: String, code: String) {
            val intent = Intent(activity, RegisterFillInfoActivity.javaClass)
            intent.putExtra(KEY_PHONE, phone)
            intent.putExtra(KEY_CODE, code)
            activity.startActivity(intent)
        }
    }
}