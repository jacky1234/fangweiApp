package com.jacky.beedee.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_account_security.*

/**
 * 2018/11/2.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
class AccountAndSecurityActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_security)

        titleView.setLeftAction(View.OnClickListener { finish() })

    }
}
