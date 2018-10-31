package com.jacky.beedee.ui

import com.jacky.beedee.ui.fragment.HomeFragment
import com.jacky.beedee.ui.inner.arch.BaseRootSupportActivity


class MainActivity : BaseRootSupportActivity<HomeFragment>() {
    override fun createClazz(): Class<HomeFragment> {
        return HomeFragment::class.java
    }

    override fun createSupportFragment(): HomeFragment {
        return HomeFragment()
    }
}
