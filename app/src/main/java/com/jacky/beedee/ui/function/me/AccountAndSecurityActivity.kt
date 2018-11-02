package com.jacky.beedee.ui.function.me

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.jacky.beedee.R
import com.jacky.beedee.logic.MiscFacade
import com.jacky.beedee.logic.entity.MySelf
import com.jacky.beedee.support.ext.clickWithTrigger
import com.jacky.beedee.support.ext.then
import com.jacky.beedee.support.image.MyGlideEngin
import com.jacky.beedee.support.log.Logger
import com.jacky.beedee.support.util.AndroidUtil
import com.jacky.beedee.support.util.Strings
import com.jacky.beedee.ui.Dialog.DialogHelper
import com.jacky.beedee.ui.inner.arch.BaseActivity
import com.jacky.beedee.ui.widget.RowItemView
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.activity_account_security.*


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


        parent_head.setType(RowItemView.FLAG_RIGHT_SHOW_IMAGE)
        parent_head.setTitle("头像")

        parent_nickname.setType(RowItemView.FLAG_RIGHT_SHOW_TEXT)
        parent_nickname.setTitle("昵称")

        parent_mobile.setType(RowItemView.FLAG_RIGHT_SHOW_TEXT)
        parent_mobile.setTitle("绑定手机号")

        parent_modify_pwd.setType(RowItemView.FLAG_RIGHT_SHOW_TEXT)
        parent_modify_pwd.setTitle("修改密码")

        parent_logout.setType(RowItemView.FLAG_NONE)
        parent_logout.setTitle("退出登陆")

        parent_logout.clickWithTrigger {
            DialogHelper.createSimpleConfirmDialog(this, "确定要退出吗？", object : QMUIDialogAction.ActionListener {
                override fun onClick(dialog: QMUIDialog?, index: Int) {
                    MiscFacade.get().loginOutFlag(this@AccountAndSecurityActivity, true)
                }
            }).show()
        }
        parent_head.setOnClickListener {
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe {
                it?.let {
                    it.then({
                        Matisse.from(this)
                                .choose(MimeType.ofImage())
                                .showSingleMediaType(true)
                                .gridExpectedSize(AndroidUtil.getScreenWidth() / 3 - 10)
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .thumbnailScale(0.85f)
                                .imageEngine(MyGlideEngin())
                                .forResult(REQUEST_IMAGE_CHOOSE)
                    }, { AndroidUtil.toast("未获取存储权限") })
                }

            }
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
            Matisse.obtainResult(data)?.let {
                if (!it.isEmpty()) {
                    val realPathFromUri = AndroidUtil.getRealPathFromUri(it[0])
                    if (Strings.isNotBlank(realPathFromUri)) {
                        Logger.i(realPathFromUri!!)
                        
                    }
                }
            }
        }
    }

    private fun changeableData() {
        rxPermissions.request(Manifest.permission.INTERNET).subscribe {
            it.then({
                Glide.with(this).load(MySelf.get().avatar)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(parent_head.imageView)
            }, { AndroidUtil.toast("未获取网络权限") })
        }

        parent_nickname.setRightDesc(MySelf.get().nickName)
        parent_mobile.setRightDesc(MySelf.get().mobile)

    }

    companion object {
        val REQUEST_IMAGE_CHOOSE = 100
    }
}