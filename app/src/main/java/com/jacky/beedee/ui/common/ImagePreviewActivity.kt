package com.jacky.beedee.ui.common

import com.jacky.beedee.ui.inner.arch.BaseRootSupportActivity

class ImagePreviewActivity : BaseRootSupportActivity<ImagePreviewFragment>() {
    override fun createClazz(): Class<ImagePreviewFragment> = ImagePreviewFragment::class.java

    override fun createSupportFragment(): ImagePreviewFragment {
        return ImagePreviewFragment()
    }
}