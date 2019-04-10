package com.jacky.labeauty.ui.function.me

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.jacky.labeauty.support.ext.then
import com.jacky.labeauty.support.ext.toast
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.Strings
import com.jacky.labeauty.ui.Dialog.DialogHelper
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jacky.labeauty.ui.widget.RowItemView
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.activity_account_security.*
import java.io.File


/**
 * 2018/11/2.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
class AccountAndSecurityActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_security)

        titleView.setLeftAction(View.OnClickListener { finish() })
        titleView.setRightAction(View.OnClickListener { finish() })

        parent_head.setType(RowItemView.FLAG_RIGHT_SHOW_IMAGE)
        parent_head.setTitle("头像")

        parent_nickname.setType(RowItemView.FLAG_RIGHT_SHOW_TEXT)
        parent_nickname.setTitle("昵称")

        parent_mobile.setType(RowItemView.FLAG_RIGHT_SHOW_TEXT)
        parent_mobile.setTitle("绑定手机号")

        if (MySelf.get().isHasPassword) {
            parent_modify_pwd.setType(RowItemView.FLAG_RIGHT_SHOW_TEXT)
            parent_modify_pwd.setTitle("修改密码")
        } else {
            parent_modify_pwd.visibility = View.GONE
        }

        parent_logout.setType(RowItemView.FLAG_NONE)
        parent_logout.setTitle("退出登陆")

        //头像
        parent_head.setOnClickListener {
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe {
                it?.let {
                    it.then({
                        ImageLoader.chooseImageFromGallery(this)
                                .forResult(REQUEST_IMAGE_CHOOSE)
                    }, { AndroidUtil.toast("未获取存储权限") })
                }

            }
        }

        //登出
        parent_logout.clickWithTrigger {
            DialogHelper.createSimpleConfirmDialog(this, "确定要退出吗？") { _, _ ->
                MiscFacade.get().loginOutFlag(this@AccountAndSecurityActivity, true)
            }.show()
        }

        //nickname
        parent_nickname.clickWithTrigger {
            launch<UpdateNickNameActivity>()
        }

        //mobile
        parent_mobile.clickWithTrigger {
            launch<BindedMobileActivity>()
        }

        //pwd
        parent_modify_pwd.clickWithTrigger {
            launch<UpdatePwdActivity>()
        }
        changeableData()
    }

    override fun onResume() {
        super.onResume()
        changeableData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CHOOSE) {
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
                                        changeableData()
                                    }
                        }
                    }
                }
            }
        }
    }

    private fun changeableData() {
        rxPermissions.request(Manifest.permission.INTERNET).subscribe {
            it.then({
                Glide.with(this)
                        .setDefaultRequestOptions(ImageLoader.defaultRequestOptions)
                        .load(MySelf.get().avatar)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(parent_head.imageView)
            }, { AndroidUtil.toast("未获取网络权限") })
        }

        parent_nickname.setRightDesc(MySelf.get().nickName)
        parent_mobile.setRightDesc(MySelf.get().mobile)

    }

    companion object {
        const val REQUEST_IMAGE_CHOOSE = 100
    }
}
