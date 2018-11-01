package com.jacky.beedee.ui.function.me

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.jacky.beedee.R
import com.jacky.beedee.logic.entity.MySelf
import com.jacky.beedee.support.ext.launch
import com.jacky.beedee.ui.function.login.LoginActivity
import com.jacky.beedee.ui.inner.arch.MySupportFragment
import com.jakewharton.rxbinding2.view.RxView
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
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

    private fun initFlexible() {
        if (MySelf.get().isLogined()) {
            parentLogined.visibility = View.VISIBLE
            parentUnLogined.visibility = View.GONE
            tv_nickname.text = MySelf.get().nickName
            tv_detail.text = MySelf.get().mobile + "/" + MySelf.get().email
        } else {
            parentLogined.visibility = View.GONE
            parentUnLogined.visibility = View.VISIBLE
        }
    }

    private fun initOnce() {
        RxView.clicks(parentUnLogined).throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe {
                    activity!!.launch<LoginActivity>()
                }


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

            if (it == itemAccount) {
                activity!!.launch<AccountAndSecurityActivity>()
            }
        }

        QMUIGroupListView.newSection(context)
                .addItemView(itemFavorite, onClickListener)
                .addItemView(itemAccount, onClickListener)
                .addItemView(itemAboutUs, onClickListener)
                .addItemView(itemSetting, onClickListener)
                .addTo(groupListView)
    }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initFlexible()
        initOnce()
    }

    override fun onResume() {
        super.onResume()
        initFlexible()
    }
}