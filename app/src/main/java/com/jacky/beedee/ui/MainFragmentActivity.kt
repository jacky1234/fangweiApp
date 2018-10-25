package com.jacky.beedee.ui

import android.os.Bundle
import com.jacky.beedee.R
import com.jacky.beedee.ui.fragment.HomeFragment
import com.jacky.beedee.ui.inner.arch.BaseFragment
import com.jacky.beedee.ui.inner.arch.BaseFragmentActivity


class MainFragmentActivity : BaseFragmentActivity() {
    override fun getContextViewId(): Int {
        return R.id.beedee_id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val fragment = getFirstFragment()

            supportFragmentManager
                    .beginTransaction()
                    .add(contextViewId, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(fragment.javaClass.simpleName)
                    .commit()
        }
    }

    private fun getFirstFragment(): BaseFragment {
        return HomeFragment()
    }


}
