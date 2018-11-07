package com.jacky.beedee.ui.function.discovery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jacky.beedee.R
import com.jacky.beedee.ui.inner.arch.BaseActivity

/**
 * 2018/11/8.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    companion object {
        const val KEY_SEARCH_CODE_REQUEST = 200

        @JvmStatic
        fun start(activity: Activity, request: Int) {
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivityForResult(intent, request)
        }

    }
}