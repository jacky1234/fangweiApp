package com.jacky.sub.beedee.ui.function.main

import com.jacky.sub.beedee.ui.inner.arch.BaseRootSupportActivity

class OutFitActivity : BaseRootSupportActivity<OutFitFragment>() {
    override fun createClazz(): Class<OutFitFragment> = OutFitFragment::class.java

    override fun createSupportFragment(): OutFitFragment {
        return OutFitFragment()
    }
}