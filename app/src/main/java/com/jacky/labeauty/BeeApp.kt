package com.jacky.labeauty

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.baidu.mobstat.StatService
import com.jacky.labeauty.logic.Constant
import com.jacky.labeauty.logic.entity.module.MySelf
import com.jacky.labeauty.logic.language.LanguageUtil
import com.jacky.labeauty.support.Starter
import com.jacky.labeauty.support.log.Logger
import com.jacky.labeauty.support.util.AndroidUtil
import com.s2icode.main.S2iCodeModule
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.bugly.crashreport.CrashReport
import io.reactivex.plugins.RxJavaPlugins
import me.yokeyword.fragmentation.Fragmentation


class BeeApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Starter.init(this)
        initFragmentation()
        RxJavaPlugins.setErrorHandler(Logger.Companion::e)
        S2iCodeModule.init(this)
        initBaiduMTJ()
        initBugly()

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
            override fun createRefreshHeader(context: Context, layout: RefreshLayout): RefreshHeader {
                layout.setPrimaryColorsId(R.color.white, android.R.color.black)//全局设置主题颜色
                return ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        })

        LanguageUtil.readSet(this)
    }

    private fun initBugly() {
        CrashReport.initCrashReport(this, "7806f8141b", true)
        if (MySelf.get().isLogined) {
            CrashReport.setUserId(MySelf.get().mobile)
        }
    }

    private fun initBaiduMTJ() {
        StatService.setAppKey(Constant.BAIDU_MTJ_APPID)
        StatService.start(this)

        StatService.setDebugOn(BuildConfig.DEBUG)

        //统计Ip
        val ip = AndroidUtil.getIpAddress(this)

    }

    private fun initFragmentation() {
        Fragmentation.builder()
                .stackViewMode(Fragmentation.NONE)
//                .debug(BuildConfig.DEBUG)
                .handleException(Logger.Companion::e)
                .install()
    }
}