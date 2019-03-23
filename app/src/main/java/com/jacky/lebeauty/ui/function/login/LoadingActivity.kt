package com.jacky.lebeauty.ui.function.login

import android.Manifest
import android.os.Bundle
import com.jacky.lebeauty.R
import com.jacky.lebeauty.logic.entity.module.MySelf
import com.jacky.lebeauty.support.ext.launch
import com.jacky.lebeauty.support.util.AndroidUtil
import com.jacky.lebeauty.ui.function.main.MainActivity
import com.jacky.lebeauty.ui.inner.arch.BaseActivity

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
                .subscribe { }

        AndroidUtil.runUI({
            launch<MainActivity>()
            finish()
        }, if (MySelf.get().isLogined) 300L else 1500L)
    }
}