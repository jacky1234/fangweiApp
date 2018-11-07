package com.jacky.beedee

import android.content.Context
import android.graphics.Bitmap
import android.support.multidex.MultiDexApplication
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jacky.beedee.logic.image.ImageLoader
import com.jacky.beedee.support.Starter
import com.jacky.beedee.support.log.Logger
import com.lzy.ninegrid.NineGridView
import io.reactivex.plugins.RxJavaPlugins
import me.yokeyword.fragmentation.Fragmentation

class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Starter.init(this)
        initFragmentation()
        initGridView()
        RxJavaPlugins.setErrorHandler(Logger.Companion::e)

    }

    private fun initGridView() {
        NineGridView.setImageLoader(object : NineGridView.ImageLoader {
            override fun onDisplayImage(context: Context?, imageView: ImageView?, url: String?) {
                if (context != null && imageView != null) {
                    Glide.with(context)
                            .setDefaultRequestOptions(ImageLoader._1To1RequestOptions)
                            .load(url)
                            .into(imageView)
                }
            }

            override fun getCacheImage(url: String?): Bitmap? {
                return null
            }


        })

    }

    private fun initFragmentation() {
        Fragmentation.builder()
                .stackViewMode(Fragmentation.NONE)
                .debug(BuildConfig.DEBUG)
                .handleException(Logger.Companion::e)
                .install()
    }
}