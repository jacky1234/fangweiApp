package com.jacky.beedee.ui.function.main

import com.jacky.beedee.ui.inner.arch.BaseRootSupportActivity


class MainActivity : BaseRootSupportActivity<MainFragment>() {
    override fun createClazz(): Class<MainFragment> {
        return MainFragment::class.java
    }

    override fun createSupportFragment(): MainFragment {
        return MainFragment()
    }
}
