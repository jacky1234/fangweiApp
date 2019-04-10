package com.jacky.labeauty.ui.function.discovery

import android.app.Activity
import android.content.Intent
import com.jacky.labeauty.ui.inner.arch.BaseRootSupportActivity

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class GoodDetailActivity : BaseRootSupportActivity<GoodDetailFragment>() {
    override fun createClazz(): Class<GoodDetailFragment> {
        return GoodDetailFragment::class.java
    }

    override fun createSupportFragment(): GoodDetailFragment {
        return GoodDetailFragment.newInstance(intent.getStringExtra(KEY_GOOD_ID))
    }

    companion object {
        const val KEY_GOOD_ID = "KEY_GOOD_ID"

        @JvmStatic
        fun start(activity: Activity, id: String) {
            val intent = Intent(activity, GoodDetailActivity::class.java)
            intent.putExtra(KEY_GOOD_ID, id)
            activity.startActivity(intent)
        }
    }
}