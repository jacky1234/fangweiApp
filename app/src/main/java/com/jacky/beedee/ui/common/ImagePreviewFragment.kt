package com.jacky.beedee.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.beedee.R
import com.jacky.beedee.ui.inner.arch.MySupportFragment

class ImagePreviewFragment : MySupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewPager.adapter =
    }

    companion object {
        const val KEY_IMAGE = "KEY_IMAGE"

        @JvmStatic
        fun newInstance(image:Image): ImagePreviewFragment {
            val fragment = ImagePreviewFragment()
            val arguments = Bundle()
            arguments.putParcelable(KEY_IMAGE,image)
            fragment.arguments = arguments
            return fragment
        }
    }
}