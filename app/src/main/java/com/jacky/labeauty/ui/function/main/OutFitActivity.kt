package com.jacky.labeauty.ui.function.main

import com.jacky.labeauty.ui.inner.arch.BaseRootSupportActivity

class OutFitActivity : BaseRootSupportActivity<OutFitFragment>() {
    override fun createClazz(): Class<OutFitFragment> = OutFitFragment::class.java

    override fun createSupportFragment(): OutFitFragment {
        return OutFitFragment()
    }
}