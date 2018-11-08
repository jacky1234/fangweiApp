package com.jacky.beedee.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.module.MySelf
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.jacky.beedee.ui.widget.TitleView
import kotlinx.android.synthetic.main.activity_binden_mobile.*

/**
 * 2018/11/4.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class BindedMobileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binden_mobile)

        titleView.setFlag(TitleView.COMPOSE_LEFT_MIDDLEL_FLAG)
        titleView.setLeftAction(View.OnClickListener {
            finish()
        })

        tv_mobile_info.text = MySelf.get().mobile
        tv_confirm.clickWithTrigger {
            launch<UpdateMobileActivity>()
        }
    }
}