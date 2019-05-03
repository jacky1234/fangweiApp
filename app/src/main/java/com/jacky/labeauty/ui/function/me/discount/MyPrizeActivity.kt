package com.jacky.labeauty.ui.function.me.discount

import com.jacky.labeauty.ui.inner.arch.BaseRootSupportActivity

/**
 * 我的优惠劵
 */
class MyPrizeActivity : BaseRootSupportActivity<MyPrizeFragment>() {
    override fun createClazz(): Class<MyPrizeFragment> = MyPrizeFragment::class.java

    override fun createSupportFragment(): MyPrizeFragment {
        return MyPrizeFragment()
    }
}