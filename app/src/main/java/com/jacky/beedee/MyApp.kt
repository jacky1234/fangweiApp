package com.jacky.beedee

import android.support.multidex.MultiDexApplication
import com.jacky.beedee.support.Starter
import com.jacky.beedee.support.log.Logger
import io.reactivex.plugins.RxJavaPlugins
import me.yokeyword.fragmentation.Fragmentation

class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Starter.init(this)
        initFragmentation()
        RxJavaPlugins.setErrorHandler(Logger.Companion::e)
    }

    private fun initFragmentation() {
        Fragmentation.builder()
                .stackViewMode(Fragmentation.NONE)
                .debug(BuildConfig.DEBUG)
                .handleException(Logger.Companion::e)
                .install()
    }
}