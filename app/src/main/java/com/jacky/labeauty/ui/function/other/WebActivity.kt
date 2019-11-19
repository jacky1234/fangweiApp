package com.jacky.labeauty.ui.function.other

import android.app.Activity
import android.content.Intent
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
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
        const val KEY_TITLE = "KEY_TITLE"

        @JvmStatic
        fun launch(from: Activity, url: String, title: Int? = -1) {
            val intent = Intent(from, WebActivity::class.java)
            intent.putExtra(KEY_URL, url)
            intent.putExtra(KEY_TITLE, title)
            from.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val text = intent.getIntExtra(KEY_TITLE, -1)
        if (text > 0) {
            titleView.setLeftTextId(text)
        }
        titleView.setLeftAction(View.OnClickListener {
            webView.destroy()
            finish()
        })
        val webSettings = webView.settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上https和http混合地址默认异常处理，打开总是允许避免这种情况
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webSettings.domStorageEnabled = true
        webSettings.setSupportZoom(true)
        webSettings.defaultTextEncodingName = "utf-8"
        webView.loadUrl(intent.getStringExtra(KEY_URL))
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }
    }
}