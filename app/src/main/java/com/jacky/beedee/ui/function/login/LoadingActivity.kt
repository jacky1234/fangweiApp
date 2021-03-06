package com.jacky.beedee.ui.function.login

import android.Manifest
import android.os.Bundle
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.module.MySelf
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.ui.function.main.MainActivity
import com.jacky.beedee.ui.inner.arch.BaseActivity

/**
 * 2018/10/20.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */


class LoadingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    AndroidUtil.runUI({
                        launch<MainActivity>()
                        finish()
                    }, if (MySelf.get().isLogined) 300L else 1500L)
                }
    }
}