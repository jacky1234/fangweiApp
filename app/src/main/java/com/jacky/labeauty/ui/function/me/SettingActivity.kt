package com.jacky.labeauty.ui.function.me

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.DaoFacade
import com.jacky.labeauty.logic.MiscFacade
import com.jacky.labeauty.logic.entity.module.Language
import com.jacky.labeauty.logic.entity.module.MySelf
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.ext.launch
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.dialog.DialogHelper
import com.jacky.labeauty.ui.function.me.address.MyAddressActivity
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jacky.labeauty.ui.widget.RowItemView
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.android.synthetic.main.activity_bee_setting.*
import java.util.*

class SettingActivity : BaseActivity() {
    private var languageDia: QMUIDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bee_setting)

        titleView.setLeftAction(View.OnClickListener { finish() })
        parentPush.setType(RowItemView.FLAG_RIGHT_CHECKABLE)
        parentPush.setTitle("是否接收推送消息")
        parentPush.setChecked(DaoFacade.get().isPushOpened)
        parentPush.visibility = View.GONE

        if (MySelf.get().isHasPassword) {
            parent_modify_pwd.setType(RowItemView.FLAG_RIGHT_SHOW_TEXT)
            parent_modify_pwd.setTitle(AndroidUtil.getString(R.string.update_pwd).toString())
        } else {
            parent_modify_pwd.visibility = View.GONE
        }

        parentLan.setType(RowItemView.FLAG_RIGHT_SHOW_TEXT)
        parentLan.setTitle(AndroidUtil.getString(R.string.language))

        parent_mobile.setType(RowItemView.FLAG_RIGHT_SHOW_TEXT)
        parent_mobile.setTitle(AndroidUtil.getString(R.string.binded_mobile).toString())
        parent_mobile.setRightDesc(MySelf.get().mobile)

        parentAddress.setTitle(AndroidUtil.getString(R.string.please_input_address).toString())
        parentAddress.setType(RowItemView.FLAG_NONE)

        parentPush.setCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, _ ->
            DaoFacade.get().togglePushSetting()
        })

        val queryLanguage = queryLanguage()
        parentLan.setRightDesc(queryLanguage.desc)

        val language = Language.languages
        val items = Array(language.size) { "" }
        language.forEachIndexed { index, l ->
            items[index] = l.desc
        }

        parentLan.clickWithTrigger {
            languageDia = QMUIDialog.CheckableDialogBuilder(this)
                    .setTitle(R.string.take_effort_on_restart)
                    .addItems(items) { _, which ->
                        val choose: Language = Language.languages[which]
                        updateLanguage(choose)
                        languageDia?.dismiss()
                    }
                    .show()
        }

        //pwd
        parent_modify_pwd.clickWithTrigger {
            launch<UpdatePwdActivity>()
        }

        //mobile
        parent_mobile.clickWithTrigger {
            launch<BindedMobileActivity>()
        }

        // address
        parentAddress.clickWithTrigger {
            startActivity(Intent(this, MyAddressActivity::class.java))
        }


        // logout
        tvConfirm.clickWithTrigger {
            DialogHelper.createSimpleConfirmDialog(this, AndroidUtil.getString(R.string.confirm_quit)) { _, _ ->
                MiscFacade.get().loginOutFlag(this@SettingActivity, true)
            }.show()
        }
    }

    // 默认中文
    private fun queryLanguage(): Language {
        val languageKey = DaoFacade.get().languageKey
        if (languageKey != null) {
            val languages = Language.languages
            languages.forEach {
                if (it.key == languageKey) {
                    return it
                }
            }
        }

        return Language.defaultLanguage
    }


    private fun updateLanguage(language: Language) {
        val myLocale = Locale(language.locale)
        val res = resources
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)

        DaoFacade.get().setLanguageKey(language.key)
        MiscFacade.get().restartActvityStack(this)
    }

}