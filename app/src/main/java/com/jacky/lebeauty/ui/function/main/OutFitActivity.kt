package com.jacky.lebeauty.ui.function.main

import com.jacky.lebeauty.ui.inner.arch.BaseRootSupportActivity

class OutFitActivity : BaseRootSupportActivity<OutFitFragment>() {
    override fun createClazz(): Class<OutFitFragment> = OutFitFragment::class.java

    override fun createSupportFragment(): OutFitFragment {
        return OutFitFragment()
    }
}