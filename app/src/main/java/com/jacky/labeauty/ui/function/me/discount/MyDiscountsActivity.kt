package com.jacky.labeauty.ui.function.me.discount

import com.jacky.labeauty.ui.inner.arch.BaseRootSupportActivity

/**
 * 我的优惠劵
 */
class MyDiscountsActivity : BaseRootSupportActivity<MyDiscountsFragment>() {
    override fun createClazz(): Class<MyDiscountsFragment> = MyDiscountsFragment::class.java

    override fun createSupportFragment(): MyDiscountsFragment {
        return MyDiscountsFragment()
    }
}