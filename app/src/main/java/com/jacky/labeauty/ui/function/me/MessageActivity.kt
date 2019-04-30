package com.jacky.labeauty.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.serenegiant.common.BaseActivity
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        titleView.setLeftAction(View.OnClickListener { finish() })
    }
}