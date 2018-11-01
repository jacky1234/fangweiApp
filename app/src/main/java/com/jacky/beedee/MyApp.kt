package com.jacky.beedee

import android.app.Application
import com.jacky.beedee.support.Starter
import com.jacky.beedee.support.log.Logger
import io.reactivex.plugins.RxJavaPlugins
import me.yokeyword.fragmentation.Fragmentation

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Starter.init(this)
        initFragmentation()
        RxJavaPlugins.setErrorHandler(Logger.Companion::e)
    }

    private fun initFragmentation() {
        Fragmentation.builder()
                .stackViewMode(Fragmentation.SHAKE)
                .debug(BuildConfig.DEBUG)
                .handleException(Logger.Companion::e)
                .install()
    }
}