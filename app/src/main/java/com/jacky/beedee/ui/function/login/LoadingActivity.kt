package com.jacky.beedee.ui.function.login

import android.Manifest
import android.os.Bundle
import com.jacky.beedee.ui.inner.arch.BaseActivity

/**
 * 2018/10/20.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */


class LoadingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermissions
                .requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({ })
    }
}