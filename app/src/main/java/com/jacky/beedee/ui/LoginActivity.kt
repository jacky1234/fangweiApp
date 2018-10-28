package com.jacky.beedee.ui

import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        titleView.setLeftAction(View.OnClickListener { finish() })

    }
}