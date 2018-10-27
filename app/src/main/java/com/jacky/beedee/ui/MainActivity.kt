package com.jacky.beedee.ui

import com.jacky.beedee.ui.fragment.HomeFragment
import com.jacky.beedee.ui.inner.arch.BaseRootSupportActivity
import com.jacky.beedee.ui.inner.arch.MySupportFragment


class MainActivity : BaseRootSupportActivity<HomeFragment>() {
    override fun createClazz(): Class<HomeFragment> {
        return HomeFragment::class.java
    }

    override fun createSupportFragment(): HomeFragment {
        return HomeFragment()
    }

    private fun getFirstFragment(): MySupportFragment {
        return HomeFragment()
    }
}
