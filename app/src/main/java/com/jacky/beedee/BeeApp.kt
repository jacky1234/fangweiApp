package com.jacky.beedee

import android.support.multidex.MultiDexApplication
import com.jacky.beedee.support.Starter
import com.jacky.beedee.support.log.Logger
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