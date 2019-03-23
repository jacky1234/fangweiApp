package com.jacky.sub.beedee.ui.function.main

import com.jacky.sub.beedee.ui.inner.arch.BaseRootSupportActivity

/**
 * 2018/11/6.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class NewHotsGoodActivity : BaseRootSupportActivity<NewHotsGoodFragment>() {
    override fun createClazz(): Class<NewHotsGoodFragment> = NewHotsGoodFragment::class.java

    override fun createSupportFragment(): NewHotsGoodFragment {
        return NewHotsGoodFragment()
    }

}