package com.jacky.lebeauty.ui.common

import android.app.Activity
import android.content.Intent
import com.jacky.lebeauty.ui.inner.arch.BaseRootSupportActivity

class ImagePreviewActivity : BaseRootSupportActivity<ImagePreviewFragment>() {
    override fun createClazz(): Class<ImagePreviewFragment> = ImagePreviewFragment::class.java

    override fun createSupportFragment(): ImagePreviewFragment {
        val images = intent.getSerializableExtra(KEY_IMAGES) as ArrayList<Image>
        return ImagePreviewFragment.newInstance(images)
    }

    companion object {
        const val KEY_IMAGES = "KEY_IMAGES"

        @JvmStatic
        fun start(activity: Activity, images: ArrayList<Image>) {
            val intent = Intent(activity,ImagePreviewActivity::class.java)
            intent.putExtra(KEY_IMAGES,images)
            activity.startActivity(intent)
        }
    }
}