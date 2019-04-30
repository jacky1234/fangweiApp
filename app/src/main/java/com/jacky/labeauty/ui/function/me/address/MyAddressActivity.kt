package com.jacky.labeauty.ui.function.me.address

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Address
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.adapter.AddressRecorderAdapter
import com.jacky.labeauty.ui.widget.decoration.DividerPaddingDecoration
import com.serenegiant.common.BaseActivity
import kotlinx.android.synthetic.main.activity_my_address.*

class MyAddressActivity : BaseActivity() {
    lateinit var adapter: AddressRecorderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_address)

        titleView.setLeftAction(View.OnClickListener { finish() })

        adapter = AddressRecorderAdapter(R.layout.item_address)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.tvEdit -> {

                }
                R.id.tvDel -> {

                }
                R.id.default_address_container -> {
                    val item = this@MyAddressActivity.adapter.getItem(position)
                    if (item != null) {
                        item.isDefaultAddress = !item.isDefaultAddress
                        adapter.notifyItemChanged(position)
                    }
                }
            }
        }

        recyclerView.addItemDecoration(DividerPaddingDecoration(this
                , DividerPaddingDecoration.VERTICAL_LIST, AndroidUtil.dip2px(10F).toInt()))
        adapter.setNewData(mockData())
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
}