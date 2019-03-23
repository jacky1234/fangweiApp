package com.jacky.sub.beedee.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.sub.beedee.R
import com.jacky.sub.beedee.ui.inner.arch.MySupportFragment
import kotlinx.android.synthetic.main.fragment_image_preview.*

class ImagePreviewFragment : MySupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.offscreenPageLimit = 3
        val imagePreviewPagerAdapter = ImagePreviewPagerAdapter(childFragmentManager, null)
        imagePreviewPagerAdapter.addAll(arguments!!.getSerializable(KEY_IMAGE) as ArrayList<Image>)
        viewPager.adapter = imagePreviewPagerAdapter
        viewPager.currentItem = 0
    }

    companion object {
        private const val KEY_IMAGE = "KEY_IMAGE"

        @JvmStatic
        fun newInstance(images: ArrayList<Image>): ImagePreviewFragment {
            val fragment = ImagePreviewFragment()
            val arguments = Bundle()
            arguments.putSerializable(KEY_IMAGE, images)
            fragment.arguments = arguments
            return fragment
        }
    }
}