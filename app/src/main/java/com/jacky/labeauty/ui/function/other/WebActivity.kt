package com.jacky.labeauty.ui.function.other

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

/**
 * 2018/12/12.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class WebActivity : BaseActivity() {
    companion object {
        const val KEY_URL = "KEY_URL"

        @JvmStatic
        fun launch(from: Activity, url: String) {
            val intent = Intent(from, WebActivity::class.java)
            intent.putExtra(KEY_URL, url)
            from.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        titleView.setLeftAction(View.OnClickListener {
            webView.destroy()
            finish()
        })
        webView.loadUrl(intent.getStringExtra(KEY_URL))
    }
}