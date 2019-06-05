package com.jacky.labeauty.ui.function.defake

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.DefakeBean
import com.serenegiant.common.BaseActivity
import kotlinx.android.synthetic.main.activity_show_entity_detail.*

class ShowEntityDetailActivity : BaseActivity() {
    lateinit var bean: DefakeBean

    companion object {
        const val KEY_CODE = "KEY_CODE"
        const val KEY_DEFAKE = "KEY_DEFAKE"

        @JvmStatic
        fun launch(activity: Activity, bean: DefakeBean) {
            val intent = Intent(activity, ShowEntityDetailActivity::class.java)
            intent.putExtra(KEY_DEFAKE, bean)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_entity_detail)
        bean = intent.getSerializableExtra(KEY_DEFAKE) as DefakeBean

        titleView.setLeftAction(View.OnClickListener { finish() })
        tv_source.text = getSource()
        tv_proxy.text = getProxy()
    }

    private fun getSource(): CharSequence {
        return bean.agency?.let {
            StringBuilder().apply {
                append("代理商: ").append(it.name).appendln()
                append("代理地址：").append(it.address).appendln()
                append("代理编号：").append(it.sn)
            }.toString()
        } ?: kotlin.run {
            ""
        }
    }

    private fun getProxy(): CharSequence {
        return bean.agency?.let {
            it.intro
        } ?: kotlin.run {
            ""
        }
    }
}