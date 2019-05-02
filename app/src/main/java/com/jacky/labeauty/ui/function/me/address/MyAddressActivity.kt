package com.jacky.labeauty.ui.function.me.address

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Address
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.adapter.AddressRecorderAdapter
import com.jacky.labeauty.ui.dialog.DialogTipsHelper
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jacky.labeauty.ui.widget.EmptyView
import com.jacky.labeauty.ui.widget.decoration.DividerPaddingDecoration
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.android.synthetic.main.activity_my_address.*

class MyAddressActivity : BaseActivity() {
    private var deleteDialog: QMUIDialog? = null
    lateinit var adapter: AddressRecorderAdapter
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_address)

        titleView.setLeftAction(View.OnClickListener { finish() })

        adapter = AddressRecorderAdapter(R.layout.item_address)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            val address = adapter.getItem(position)
            when (view.id) {
                R.id.tvEdit -> {
                    EditAddressActivity.launch(this, address)
                }
                R.id.tvDel -> {
                    deleteDialog = DialogTipsHelper.createDeleteWarningDialog(this, object : DialogTipsHelper.OnConfirmListener {
                        override fun confirm() {
                            if (address != null) {
                                onDelete(address)
                            }
                            deleteDialog?.dismiss()
                        }
                    })
                    deleteDialog?.show()
                }
                R.id.default_address_container -> {
                    if (address != null) {
                        address.isDefaultAddress = !address.isDefaultAddress
                        adapter.notifyItemChanged(position)
                        RequestHelper.get().updateAddress(false, address)
                                .compose(bindToDestroy())
                                .subscribe({
                                    refreshAddresses(false)
                                }, {
                                    address.isDefaultAddress = !address.isDefaultAddress
                                    adapter.notifyItemChanged(position)
                                })
                    }
                }
            }
        }

        val footView = FootView(this)
        footView.clickWithTrigger {
            EditAddressActivity.launch(this)
        }
        adapter.addFooterView(footView)

        recyclerView.addItemDecoration(DividerPaddingDecoration(this
                , DividerPaddingDecoration.VERTICAL_LIST, AndroidUtil.dip2px(10F).toInt()))
//        adapter.setNewData(mockData())

        val contentView = AndroidUtil.getContentView(this) as ViewGroup
        adapter.emptyView = getEmptyView(contentView)
    }

    @SuppressLint("CheckResult")
    private fun onDelete(address: Address) {
        RequestHelper.get().deleteAddress(address.id)
                .compose(bindToDestroy())
                .subscribe {
                    if (it) {
                        AndroidUtil.toast("删除成功!")
                    }
                }
    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()
        refreshAddresses(true)
    }

    @SuppressLint("CheckResult")
    private fun refreshAddresses(loading: Boolean) {
        RequestHelper.get()
                .requestAddresses(loading)
                .compose(bindToDestroy())
                .subscribe {
                    if (it != null) {
                        adapter.setNewData(it)
                    }
                }
    }

    private fun getEmptyView(contentView: ViewGroup): View {
        val view = LayoutInflater.from(this).inflate(R.layout.empty_address_recorder, contentView, false)
        view.findViewById<EmptyView>(R.id.emptyView)
                .setImageResource(R.drawable.empty_address)
                .setDescID(R.string.empty_address)
        view.findViewById<View>(R.id.tvAddAddress).clickWithTrigger {
            EditAddressActivity.launch(this)
        }
        return view
    }


    private fun mockData(): List<Address> {
        val arrayList = ArrayList<Address>()
        for (i in 0 until 100) {
            val address = Address()
            address.id = i.toString()
            address.name = "name$i"
            address.province = "浙江省"
            address.city = "杭州市"
            address.area = "江干区"
            address.address = "克亚时代广场1106室"
            address.mobile = "18516606250"
            address.isDefaultAddress = i == 10
            arrayList.add(address)
        }
        return arrayList
    }

    private class FootView constructor(context: Context) : TextView(context) {
        init {
            val layoutParams = LinearLayout.LayoutParams(AndroidUtil.dip2px(280f).toInt(),
                    AndroidUtil.dip2px(45f).toInt())
            layoutParams.topMargin = AndroidUtil.dip2px(45f).toInt()
            layoutParams.gravity = Gravity.CENTER
            gravity = Gravity.CENTER
            setText(R.string.add_get_prize_info)
            setBackgroundResource(R.drawable.stroke_round_blue)
            setTextColor(resources.getColor(R.color.labe_blue))
            textSize = 15f
            setLayoutParams(layoutParams)
        }
    }
}