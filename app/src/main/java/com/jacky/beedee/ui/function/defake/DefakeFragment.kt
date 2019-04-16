package com.jacky.beedee.ui.function.defake

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.jacky.beedee.R
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.ext.then
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.s2icode.dao.S2iCodeResult
import com.s2icode.dao.S2iCodeResultBase
import com.s2icode.main.S2iCodeModule
import com.s2icode.main.S2iCodeResultInterface
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class DefakeFragment : MySupportFragment(), S2iCodeResultInterface {
    companion object {
        const val TAG = "DefakeFragment"
    }

    override fun onS2iCodeError(p0: S2iCodeResultBase) {
        Log.i(TAG, p0.toString())
    }

    override fun onS2iCodeResult(p0: S2iCodeResult) {
        Log.i(TAG, p0.toString())
    }

    lateinit var rxPermissions: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermissions = RxPermissions(getActivity()!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(R.layout.fragment_defake_entrance, null)
        val btnScan = content.findViewById<FrameLayout>(R.id.parent_scan)
        btnScan.clickWithTrigger {
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.INTERNET)
                    .subscribe {
                        it.then({
                            if (isAttached()) {
                                if(Build.VERSION.SDK_INT < 21){
                                    AndroidUtil.toast("抱歉，防伪功能只支持android5.0及以上系统，请升级您的系统")
                                    return@then
                                }
                                S2iCodeModule.setS2iCodeResultInterface(this)
                                S2iCodeModule.startS2iCamera(true)
                            }
                        }, {
                            AndroidUtil.toast("请开启存储和相机权限")
                        })
                    }
        }

        return content
    }

    override fun onDestroy() {
        super.onDestroy()
        S2iCodeModule.setS2iCodeResultInterface(null)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }
}