package com.jacky.sub.beedee.ui.function.discovery

import android.app.Activity
import android.content.Intent
import com.jacky.sub.beedee.ui.inner.arch.BaseRootSupportActivity

/**
 * 2018/11/8.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class SearchActivity : BaseRootSupportActivity<SearchFragment>() {
    override fun createClazz(): Class<SearchFragment> = SearchFragment::class.java

    override fun createSupportFragment(): SearchFragment {
        return SearchFragment()
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