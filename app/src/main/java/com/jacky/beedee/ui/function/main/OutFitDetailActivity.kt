package com.jacky.beedee.ui.function.main

import android.app.Activity
import android.content.Intent
import com.jacky.beedee.ui.inner.arch.BaseRootSupportActivity

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class OutFitDetailActivity : BaseRootSupportActivity<OutFitDetailFragment>() {
    override fun createClazz(): Class<OutFitDetailFragment> {
        return OutFitDetailFragment::class.java
    }

    override fun createSupportFragment(): OutFitDetailFragment {
        return OutFitDetailFragment.newInstance(intent.getStringExtra(KEY_OUTFIT_ID))
    }

    companion object {
        const val KEY_OUTFIT_ID = "KEY_OUTFIT_ID"

        @JvmStatic
        fun start(activity: Activity, id: String) {
            val intent = Intent(activity, OutFitDetailActivity::class.java)
            intent.putExtra(KEY_OUTFIT_ID, id)
            activity.startActivity(intent)
        }
    }
}