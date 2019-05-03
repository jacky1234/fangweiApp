package com.jacky.labeauty.ui.function.me.prize

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_show_success.*

class ShowSuccessActivity : BaseActivity() {
    companion object {
        const val KEY_TYPE = "KEY_TYPE"
        const val TYPE_PRIZE = 1
        const val TYPE_FEEDBACK = 2

        @JvmStatic
        fun launch(from: Activity, type: Int) {
            val intent = Intent(from, ShowSuccessActivity::class.java)
            intent.putExtra(KEY_TYPE, type)
            from.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_success)

        titleView.setLeftAction(View.OnClickListener { finish() })

        val type = intent.getIntExtra(KEY_TYPE, TYPE_PRIZE)
        when (type) {
            TYPE_PRIZE -> {
                textView6.setText(R.string.extract_prize_success)
                textView7.setText(R.string.extract_prize_success_tip)
            }
            TYPE_FEEDBACK -> {
                textView6.setText(R.string.submit_success)
                textView7.setText(R.string.submit_success_tip)
            }
        }

        tvConfirm.clickWithTrigger {
            finish()
        }
    }
}