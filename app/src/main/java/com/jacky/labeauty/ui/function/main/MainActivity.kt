package com.jacky.labeauty.ui.function.main

import android.app.Activity
import android.content.Intent
import com.jacky.labeauty.ui.inner.arch.BaseRootSupportActivity


class MainActivity : BaseRootSupportActivity<MainFragment>() {
    override fun createClazz(): Class<MainFragment> {
        return MainFragment::class.java
    }

    override fun createSupportFragment(): MainFragment {
        return MainFragment()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        findFragment(MainFragment::class.java)?.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        @JvmStatic
        fun launch(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
        }
    }
}
