package com.jacky.beedee.ui.function.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jacky.beedee.R
import com.jacky.beedee.logic.image.ImageLoader
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jacky.beedee.ui.widget.looper.LooperPagerAdapter
import kotlinx.android.synthetic.main.fragment_detail_outfit.*

class OutFitDetailFragment : MySupportFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_outfit, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleView.setLeftAction(View.OnClickListener { pop() })

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        val id = arguments?.getString(KEY_OUTFITID)
        RequestHelper.get().requestOutfitDetail(id)
                .compose(bindUntilDetach())
                .subscribe {
                    viewPager.setAutoScroll(true, 5000)
                    viewPager.adapter = LooperPagerAdapter(it.gallery.size) { position ->
                        val imageView = ImageView(activity)
                        if (isAttached()) {
                            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                            imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                            Glide.with(this)
                                    .setDefaultRequestOptions(ImageLoader._16To9RequestOptions)
                                    .load(it.gallery[position])
                                    .into(imageView)
                        }

                        imageView
                    }
                    circleIndicator.setViewPager(viewPager)
                }
    }

    companion object {
        const val KEY_OUTFITID = "KEY_OUTFITID"

        @JvmStatic
        fun newInstance(id: String): OutFitDetailFragment {
            val fragment = OutFitDetailFragment()
            val bundle = Bundle()
            bundle.putString(KEY_OUTFITID, id)
            fragment.arguments = bundle
            return fragment
        }
    }
}
