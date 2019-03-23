package com.jacky.lebeauty.ui.function.me

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.jacky.lebeauty.R
import com.jacky.lebeauty.support.ext.clickWithTrigger
import com.jacky.lebeauty.support.ext.launch
import com.jacky.lebeauty.support.system.DeviceDependency
import com.jacky.lebeauty.ui.inner.arch.BaseActivity
import com.jacky.lebeauty.ui.widget.RowItemView
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : BaseActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        titleView.setLeftAction(View.OnClickListener { finish() })

        tvVersion.text = "版本 V${DeviceDependency.current.device.appVersion}"
        parentAppIntroduce.setTitle("APP说明")
        parentAppIntroduce.setType(RowItemView.FLAG_NONE)

        parentFeedback.setTitle("问题反馈")
        parentFeedback.setType(RowItemView.FLAG_NONE)

        parentAppIntroduce.clickWithTrigger {
            launch<AppIntroduceActivity>()
        }

        parentFeedback.clickWithTrigger {
            launch<FeedbackProblemActivity>()
        }
    }
}