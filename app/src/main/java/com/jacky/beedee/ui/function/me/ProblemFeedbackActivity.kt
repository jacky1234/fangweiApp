package com.jacky.beedee.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.beedee.R
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_problem_feedback.*

class ProblemFeedbackActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_feedback)

        titleView.setLeftAction(View.OnClickListener { finish() })
        tvConfirm.clickWithTrigger {
            val text = editText.text.toString()
//            TODO("feedback")
        }
    }
}