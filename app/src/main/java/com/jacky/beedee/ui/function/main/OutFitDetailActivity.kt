package com.jacky.beedee.ui.function.main

import android.app.Activity
import android.content.Intent
import com.jacky.beedee.ui.inner.arch.BaseRootSupportActivity

class OutFitDetailActivity : BaseRootSupportActivity<OutFitDetailFragment>() {
    override fun createClazz(): Class<OutFitDetailFragment> = OutFitDetailFragment::class.java

    override fun createSupportFragment(): OutFitDetailFragment {
        return OutFitDetailFragment.newInstance(intent.getStringExtra(KEY_OUTFITID))
    }

    companion object {
        const val KEY_OUTFITID = "KEY_OUTFITID"

        @JvmStatic
        fun start(activity: Activity, id: String) {
            val intent = Intent(activity, OutFitDetailActivity::class.java)
            intent.putExtra(KEY_OUTFITID, id)
            activity.startActivity(intent)
        }
    }
}