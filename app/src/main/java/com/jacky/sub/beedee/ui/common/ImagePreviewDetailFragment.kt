package com.jacky.sub.beedee.ui.common

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.jacky.sub.beedee.R
import com.jacky.sub.beedee.support.util.AndroidUtil
import com.jacky.sub.beedee.ui.inner.arch.MySupportFragment
import kotlinx.android.synthetic.main.fragment_image_preview_detail.*

/**
 * 2018/11/10.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class ImagePreviewDetailFragment : MySupportFragment() {

    val placeHolder = R.mipmap.item_empty_1_1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_preview_detail, container, false)
    }

    private val target = object : SimpleTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            if (isAttached()) {
                if (resource is BitmapDrawable) {
                    imageView.setImage(ImageSource.bitmap(resource.bitmap))
                } else {
                    imageView.setImage(ImageSource.bitmap(AndroidUtil.drawableToBitmap(resource)))
                }
            }
        }

        override fun onLoadStarted(placeholder: Drawable?) {
            if (isAttached()) {
                imageView.setImage(ImageSource.resource(placeHolder))
            }
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            if (isAttached()) {
                imageView.setImage(ImageSource.resource(placeHolder))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = arguments!!.getParcelable(KEY_IMAGE) as Image

        Glide.with(context!!)
                .load(image.origin)
                .into(target)

    }

    companion object {
        const val KEY_IMAGE = "KEY_IMAGE"

        @JvmStatic
        fun newInstance(image: Image): ImagePreviewDetailFragment {
            val fragment = ImagePreviewDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_IMAGE, image)
            fragment.arguments = bundle
            return fragment
        }
    }
}