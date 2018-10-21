package com.jacky.beedee.ui

import android.Manifest
import android.os.Bundle
import com.jacky.beedee.ui.inner.BaseActivity
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * 2018/10/20.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */


class LoadingActivity : BaseActivity() {
    lateinit var rxPermissions: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermissions = RxPermissions(this)
        rxPermissions
                .requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe( {  })
    }
}