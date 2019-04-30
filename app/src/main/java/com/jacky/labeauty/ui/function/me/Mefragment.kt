package com.jacky.labeauty.ui.function.me

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.MiscFacade
import com.jacky.labeauty.logic.entity.module.MySelf
import com.jacky.labeauty.logic.image.ImageLoader
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.ext.launch
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.AndroidUtil.toast
import com.jacky.labeauty.support.util.Strings
import com.jacky.labeauty.ui.function.login.LoginActivity
import com.jacky.labeauty.ui.function.me.discount.MyDiscountsActivity
import com.jacky.labeauty.ui.function.me.integral.MyIntegralActivity
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import com.jakewharton.rxbinding2.view.RxView
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListSectionHeaderFooterView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.fragment_me.*
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 2018/11/1.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */
class Mefragment : MySupportFragment() {
    private lateinit var parentUnLogined: View
    private lateinit var parentLogined: View
    private lateinit var groupListView: QMUIGroupListView
    private lateinit var tv_nickname: TextView
    private lateinit var tv_detail: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = inflater.inflate(R.layout.fragment_me, null)
        parentUnLogined = content.findViewById<View>(R.id.parent_unlogined)
        parentLogined = content.findViewById<View>(R.id.parent_login)
        groupListView = content.findViewById(R.id.groupListView)
        tv_nickname = content.findViewById(R.id.tv_nickname)
        tv_detail = content.findViewById(R.id.tv_detail)
        return content
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivHeader.clickWithTrigger {
            ImageLoader.chooseImageFromGallery(_mActivity)
                    .forResult(200)
        }
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    private fun initFlexible() {
        RxView.clicks(parentUnLogined).throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe {
                    activity!!.launch<LoginActivity>()
                }

        if (MySelf.get().isLogined) {
            parentLogined.visibility = View.VISIBLE
            parentUnLogined.visibility = View.GONE
            tv_nickname.text = MySelf.get().showingName
            if (Strings.isNotBlank(MySelf.get().email)) {
                tv_detail.text = "${MySelf.get().mobile}/${MySelf.get().email}"
            } else {
                tv_detail.text = MySelf.get().mobile
            }
        } else {
            parentLogined.visibility = View.GONE
            parentUnLogined.visibility = View.VISIBLE
        }

        Glide.with(this)
                .setDefaultRequestOptions(ImageLoader.defaultRequestOptions)
                .load(MySelf.get().avatar)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(ivHeader)
    }

    private fun initOnce() {
        val itemScore = groupListView.createItemView(
                ContextCompat.getDrawable(context!!, R.mipmap.me_integral),
                "我的积分",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        )

        val itemDiscounts = groupListView.createItemView(
                ContextCompat.getDrawable(context!!, R.mipmap.me_coupon),
                "优惠劵",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        )

        val itemSetting = groupListView.createItemView(
                ContextCompat.getDrawable(context!!, R.mipmap.me_setting),
                "设置",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        )

        val itemAboutUs = groupListView.createItemView(
                ContextCompat.getDrawable(context!!, R.mipmap.my_about),
                "关于我们",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        )

        val onClickListener = View.OnClickListener {
            when (it) {
                itemScore -> {
                    ensureLogin(it, Runnable {
                        startActivity(Intent(getActivity(), MyIntegralActivity::class.java))
                    })
                }

                itemDiscounts -> {
                    ensureLogin(it, Runnable {
                        startActivity(Intent(this@Mefragment.getActivity(), MyDiscountsActivity::class.java))
                    })
                }

                itemSetting -> {
                    ensureLogin(it, Runnable {
                        activity!!.launch<SettingActivity>()
                    })
                }

                itemAboutUs -> {
                    activity!!.launch<AboutUsActivity>()
                }
            }
        }

        groupListView.separatorStyle = QMUIGroupListView.SEPARATOR_STYLE_NONE
        QMUIGroupListView.newSection(context)
                .addItemView(itemScore, onClickListener)
                .addItemView(itemDiscounts, onClickListener)
                .addItemView(itemSetting, onClickListener)
                .addItemView(itemAboutUs, onClickListener)
                .addTo(groupListView)

        val childCount = groupListView.childCount
        for (i in 0 until childCount) {
            if (groupListView.getChildAt(i) is QMUIGroupListSectionHeaderFooterView) {
                groupListView.getChildAt(i).visibility = View.GONE
            }
        }

        tv_msg.clickWithTrigger {
            startActivity(Intent(getActivity(), MessageActivity::class.java))
        }
    }

    private fun ensureLogin(view: View, runnable: Runnable?) {
        if (MySelf.get().isLogined) {
            runnable?.run()
        } else {
            MiscFacade.get().setLastRunnable {
                view.performClick()
            }
            activity.launch<LoginActivity>()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 200) {
            if (data != null) {
                Matisse.obtainResult(data)?.let {
                    if (!it.isEmpty()) {
                        val realPathFromUri = AndroidUtil.getRealPathFromUri(it[0])
                        if (Strings.isNotBlank(realPathFromUri)) {
                            RequestHelper.get().uploadFile(File(realPathFromUri))
                                    .compose(bindToLifecycle())
                                    .subscribe {
                                        toast("修改成功")
                                        MySelf.get().avatar = it.url
                                        MySelf.get().save()
                                        initFlexible()
                                    }
                        }
                    }
                }
            }
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initOnce()
        initFlexible()
    }

    override fun onResume() {
        super.onResume()
        initFlexible()
    }
}