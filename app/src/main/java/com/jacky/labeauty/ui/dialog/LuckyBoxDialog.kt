package com.jacky.labeauty.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Prize
import com.jacky.labeauty.support.util.AndroidUtil
import kotlinx.android.synthetic.main.dialog_open_lucky_box.*

class LuckyBoxDialog(private val getPrizeClickListener: OnGetPrizeClickListener?
                     , private val prize: Prize, mContext: Context) : AlertDialog(mContext, R.style.dialog) {

    override fun onStart() {
        super.onStart()
        val window = window
        if (window != null) {
            val params = window.attributes
            params.gravity = Gravity.CENTER
            params.width = AndroidUtil.dip2px(280f).toInt()
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = params
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_open_lucky_box)
        initView()
    }

    private fun initView() {
        // 点击空白区域不消失
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        parent_gift.visibility = View.GONE
        parent_coupon.visibility = View.GONE
        parent_integral.visibility = View.GONE
        parent_np.visibility = View.GONE

        val targetType = prize.targetType
        if (targetType != Prize.TARGET_TYPE_EMPTY) {
            tv_title.setText(R.string.get_prize_title)

            when (targetType) {
                Prize.TARGET_TYPE_GIFT -> {
                    parent_gift.visibility = View.VISIBLE

                    loadImage(97, gift_iv_thumb)
                    gift_tvDesc.text = prize.name
                    tvButton.setText(R.string.extract_prize_instant)
                }
                Prize.TARGET_TYPE_COUPON -> {
                    parent_coupon.visibility = View.VISIBLE

                    loadImage(148, coupon_iv_thumb)
                    tvButton.setText(R.string.tap_to_get)
                }
                Prize.TARGET_TYPE_INTEGRAL -> {
                    parent_integral.visibility = View.VISIBLE

                    loadImage(120, iv_integral)
//                    tvIntegralNumber.text = prize.name
                    tvButton.setText(R.string.tap_to_get)
                }
            }

        } else {
            parent_np.visibility = View.VISIBLE
            tv_title.setText(R.string.no_prize_title)
            tvButton.text = "继续抽奖"
        }

        tvButton.setOnClickListener {
            if (targetType == Prize.TARGET_TYPE_GIFT) {
                getPrizeClickListener?.getPrizeClickListener()
            }
            dismiss()
        }

        tvDelete.setOnClickListener {
            dismiss()
        }
    }

    private fun loadImage(baseDp: Int, imageView: ImageView) {
        val drawableImageViewTarget = object : DrawableImageViewTarget(imageView) {
            override fun setResource(resource: Drawable?) {
                if (resource != null) {
                    val layoutParams = imageView.layoutParams
                    layoutParams.width = AndroidUtil.dip2px(baseDp.toFloat()).toInt()
                    layoutParams.height = ((resource.intrinsicHeight * 1.0f / resource.intrinsicWidth) * layoutParams.width).toInt()
                    imageView.layoutParams = layoutParams
                }

                super.setResource(resource)
            }
        }

        Glide.with(context)
                .load(prize.thumb)
                .into(drawableImageViewTarget)
    }

    interface OnGetPrizeClickListener {
        fun getPrizeClickListener()
    }
}