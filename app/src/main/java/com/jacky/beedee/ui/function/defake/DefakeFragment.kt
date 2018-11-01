package com.jacky.beedee.ui.function.defake

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.jacky.beedee.R
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.ext.then
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class DefakeFragment : MySupportFragment() {
    lateinit var rxPermissions: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermissions = RxPermissions(getActivity()!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(R.layout.fragment_defake_entrance, null)
        val btnScan = content.findViewById<FrameLayout>(R.id.parent_scan)
        btnScan.clickWithTrigger {
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .subscribe {
                        it.then({
                            if (isAttached()) {
                                activity!!.launch<DefakeDetailActivity>()
                            }
                        }, {
                            AndroidUtil.toast("请开启存储和相机权限")
                        })
                    }
        }

        return content
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }
}