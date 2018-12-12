package com.jacky.beedee

import android.support.multidex.MultiDexApplication
import com.baidu.mobstat.StatService
import com.jacky.beedee.logic.Constant
import com.jacky.beedee.support.Starter
import com.jacky.beedee.support.log.Logger
import com.jacky.beedee.support.util.AndroidUtil
import com.s2icode.main.S2iCodeModule
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
    }

    private fun initBaiduMTJ() {
        StatService.setAppKey(Constant.BAIDU_MTJ_APPID)
        StatService.start(this)

        StatService.setDebugOn(BuildConfig.DEBUG)

        //统计Ip
        val ip = AndroidUtil.getIpAddress(this)
        
    }

    //nineGridView
//    private fun initGridView() {
//        NineGridView.setImageLoader(object : NineGridView.ImageLoader {
//            override fun onDisplayImage(context: Context?, imageView: ImageView?, url: String?) {
//                if (context != null && imageView != null) {
//                    Glide.with(context)
//                            .setDefaultRequestOptions(ImageLoader._1To1RequestOptions)
//                            .load(url)
//                            .into(imageView)
//                }
//            }
//
//            override fun getCacheImage(url: String?): Bitmap? {
//                return null
//            }
//        })
//    }

    private fun initFragmentation() {
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .handleException(Logger.Companion::e)
                .install()
    }
}