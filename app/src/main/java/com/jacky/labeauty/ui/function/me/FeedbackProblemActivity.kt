package com.jacky.labeauty.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.Strings
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_problem_feedback.*

class FeedbackProblemActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_feedback)

        titleView.setLeftAction(View.OnClickListener { finish() })
        tvConfirm.clickWithTrigger {
            val text = editText.text.toString()
            if (Strings.isNullOrEmpty(text)) {
                AndroidUtil.toast("请输入反馈内容")
                return@clickWithTrigger
            }

//            RequestHelper.get()
//                    .feedbackProblem(text)
//                    .compose(bindToDestroy())
//                    .subscribe {
//                        AndroidUtil.toast("感谢您的反馈")
//                        finish()
//                    }
        }
    }
}