package com.jacky.beedee.ui.function.main

import android.app.Activity
import android.content.Intent
import com.jacky.beedee.ui.inner.arch.BaseRootSupportActivity

class OutFitActivity : BaseRootSupportActivity<OutFitFragment>() {
    override fun createClazz(): Class<OutFitFragment> = OutFitFragment::class.java

    override fun createSupportFragment(): OutFitFragment {
//        return OutFitDetailFragment.newInstance(intent.getStringExtra(KEY_OUTFITID))

        return OutFitFragment()
    }

    companion object {
        const val KEY_OUTFITID = "KEY_OUTFITID"

        @JvmStatic
        fun start(activity: Activity, id: String) {
            val intent = Intent(activity, OutFitActivity::class.java)
            intent.putExtra(KEY_OUTFITID, id)
            activity.startActivity(intent)
        }
    }
}