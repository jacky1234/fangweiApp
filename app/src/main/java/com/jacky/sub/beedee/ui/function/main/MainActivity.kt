package com.jacky.sub.beedee.ui.function.main

import android.content.Intent
import com.jacky.sub.beedee.ui.inner.arch.BaseRootSupportActivity


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
}
