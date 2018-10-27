package com.jacky.beedee

import android.app.Application
import com.jacky.beedee.support.log.Logger
import me.yokeyword.fragmentation.Fragmentation

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initFragmentation()
    }

    private fun initFragmentation() {
        Fragmentation.builder()
                .stackViewMode(Fragmentation.NONE)
                .debug(BuildConfig.DEBUG)
                .handleException(Logger.Companion::e)
                .install()
    }
}