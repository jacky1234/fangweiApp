package com.jacky.beedee.ui.function.me

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.jacky.beedee.R
import com.jacky.beedee.logic.MiscFacade
import com.jacky.beedee.logic.entity.module.MySelf
import com.jacky.beedee.logic.image.ImageLoader
import com.jacky.beedee.logic.network.RequestHelper
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.support.util.AndroidUtil.toast
import com.jacky.beedee.support.util.Strings
import com.jacky.beedee.ui.function.login.LoginActivity
import com.jacky.beedee.ui.function.me.favorite.MyFavoriteActivity
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jakewharton.rxbinding2.view.RxView
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
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

    @SuppressLint("SetTextI18n")
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
        val itemFavorite = groupListView.createItemView(
                ContextCompat.getDrawable(context!!, R.mipmap.ic_favorite),
                "我的收藏",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        )

        val itemAccount = groupListView.createItemView(
                ContextCompat.getDrawable(context!!, R.mipmap.ic_account),
                "账户安全",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        )

        val itemAboutUs = groupListView.createItemView(
                ContextCompat.getDrawable(context!!, R.mipmap.ic_about_us),
                "关于我们",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        )

        val itemSetting = groupListView.createItemView(
                ContextCompat.getDrawable(context!!, R.mipmap.ic_setting),
                "设置",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        )

        val onClickListener = View.OnClickListener {
            if (it is QMUICommonListItemView) {
                val text = it.text
                Toast.makeText(getActivity(), text.toString() + " is Clicked", Toast.LENGTH_SHORT).show()
            }

            when (it) {
                itemFavorite -> {
                    if (MySelf.get().isLogined) {
                        activity!!.launch<MyFavoriteActivity>()
                    } else {
                        MiscFacade.get().setLastRunnable {
                            itemFavorite.performClick()
                        }
                        activity.launch<LoginActivity>()
                    }
                }

                itemAccount -> {
                    if (MySelf.get().isLogined) {
                        activity!!.launch<AccountAndSecurityActivity>()
                    } else {
                        MiscFacade.get().setLastRunnable {
                            itemAccount.performClick()
                        }
                        activity.launch<LoginActivity>()
                    }
                }

                itemAboutUs -> {
                    activity!!.launch<AboutUsActivity>()
                }

                itemSetting -> {
                    activity!!.launch<SettingActivity>()
                }
            }
        }

        QMUIGroupListView.newSection(context)
                .addItemView(itemFavorite, onClickListener)
                .addItemView(itemAccount, onClickListener)
                .addItemView(itemAboutUs, onClickListener)
                .addItemView(itemSetting, onClickListener)
                .addTo(groupListView)
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