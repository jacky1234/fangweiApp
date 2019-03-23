package com.jacky.sub.beedee.ui.inner.arch

import android.os.Bundle
import android.widget.FrameLayout
import com.jacky.sub.beedee.R

abstract class BaseRootSupportActivity<T : MySupportFragment> : BaseActivity() {
    private var fragment: T? = null
    private lateinit var clazz: Class<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val container = FrameLayout(this)
            container.id = R.id.baseroot_activity_container_id
            setContentView(container)
            clazz = createClazz()
            createSupportFragmentInternal()
            loadRootFragment(R.id.baseroot_activity_container_id, fragment!!)
        }
    }

    protected abstract fun createClazz(): Class<T>

    private fun createSupportFragmentInternal() {
        fragment = findFragment(clazz)
        if (fragment == null) {
            fragment = createSupportFragment()
        }
    }

    abstract fun createSupportFragment(): T
    fun getFragment(): T {
        return fragment!!
    }
}
