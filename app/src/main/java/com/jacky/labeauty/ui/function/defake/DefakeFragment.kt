package com.jacky.labeauty.ui.function.defake

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.qrscanlibrary.QrScanFacade
import com.example.qrscanlibrary.ZxingImageHelper
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.MiscFacade
import com.jacky.labeauty.logic.entity.module.MySelf
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.ext.launch
import com.jacky.labeauty.support.ext.then
import com.jacky.labeauty.support.log.Logger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.function.login.LoginActivity
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
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

    override fun onResume() {
        super.onResume()

        if (MiscFacade.get().lastRunnable != null) {
            MiscFacade.get().lastRunnable.run()
            MiscFacade.get().lastRunnable = null
        }
    }

    var chooseDialog: QMUIDialog? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(com.jacky.labeauty.R.layout.fragment_defake_entrance, null)
        val btnScan = content.findViewById<FrameLayout>(com.jacky.labeauty.R.id.parent_scan)
        btnScan.clickWithTrigger {
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.INTERNET)
                    .subscribe {
                        it.then({
                            if (MySelf.get().isLogined) {
                                doFake()
                            } else {
                                MiscFacade.get().setLastRunnable {
                                    if (isAttached() && MySelf.get().isLogined) {
                                        doFake()
                                    }
                                }
                                activity.launch<LoginActivity>()
                            }
                        }, {
                            AndroidUtil.toast(R.string.please_open_storage_camera_permission)
                        })
                    }
        }

        return content
    }

    private fun doFake() {
        if (isAttached()) {
            if (MySelf.get().role == "MANAGER" || MySelf.get().role == "ADMIN") {
                val items = arrayOf(AndroidUtil.getString(R.string.defake), AndroidUtil.getString(R.string.follow_source))
                chooseDialog = QMUIDialog.CheckableDialogBuilder(getActivity())
                        .addItems(items) { dialog, which ->
                            when (which) {
                                0 -> {
                                    S2iCodeModule.setS2iCodeResultInterface(this@DefakeFragment)
                                    S2iCodeModule.startS2iCamera(true)
                                }
                                1 -> {
                                    QrScanFacade.start(activity!!, object : ZxingImageHelper.OnDecodeListener {
                                        override fun onDecodeSuccess(result: String) {
                                            Logger.i("decode success $result")
                                            requestQrResult(result)
                                        }

                                        override fun onDecodeFail(e: Throwable?) {
                                            Logger.i("decode error")
                                        }
                                    })
                                }
                            }
                            chooseDialog?.dismiss()
                        }
                        .show()
            } else {
                S2iCodeModule.setS2iCodeResultInterface(this@DefakeFragment)
                S2iCodeModule.startS2iCamera(true)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun requestQrResult(result: String) {
        RequestHelper.get().requestCommodity(result)
                .compose(bindUntilDetach())
                .subscribe {
                    ShowEntityDetailActivity.launch(activity!!, it)
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        S2iCodeModule.setS2iCodeResultInterface(null)
    }
}