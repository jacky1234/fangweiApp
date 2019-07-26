package com.jacky.labeauty.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.MySelf
import com.jacky.labeauty.logic.entity.request.UpdateUserRequest
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.ext.launch
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.Checker
import com.jacky.labeauty.ui.function.main.MainActivity
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jacky.labeauty.ui.widget.TitleView
import kotlinx.android.synthetic.main.activity_update_pwd.*

/**
 * 2018/11/4.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class UpdatePwdActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pwd)

        titleView.setFlag(TitleView.COMPOSE_LEFT_MIDDLEL_FLAG)
        titleView.setLeftAction(View.OnClickListener {
            finish()
        })

        tv_confirm.clickWithTrigger {
            if (Checker.check(et_old_pwd, "请输入旧密码") &&
                    Checker.check(et_new_pwd, "请输入新密码") &&
                    Checker.check(et_confirm_new_pwd, "请再次输入新密码") &&
                    Checker.equalsContent(et_new_pwd, et_confirm_new_pwd, "两次输入的密码不一致")) {
                val oldPwd = et_old_pwd.text.toString().trim()
                val newPwd = et_new_pwd.text.toString().trim()
                RequestHelper.get().changePwd(oldPwd,newPwd)
                        .compose(bindToDestroy())
                        .subscribe {
                            AndroidUtil.toast(R.string.update_success)
                            MainActivity.launch(this@UpdatePwdActivity)
                        }
            }
        }
    }

}