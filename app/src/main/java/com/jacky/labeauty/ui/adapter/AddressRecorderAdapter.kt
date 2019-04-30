package com.jacky.labeauty.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Address
import com.jacky.labeauty.logic.entity.module.MySelf

class AddressRecorderAdapter constructor(itemLayoutId: Int) : BaseQuickAdapter<Address, BaseViewHolder>(itemLayoutId) {
    override fun convert(holder: BaseViewHolder, item: Address) {
        holder.setText(R.id.tvName, MySelf.get().nickName)
                .setText(R.id.tvPhone, item.mobile)
                .setText(R.id.tvAddress, item.address)
                .setChecked(R.id.cb_checked, item.isDefaultAddress)
                .addOnClickListener(R.id.tvEdit)
                .addOnClickListener(R.id.tvDel)
                .addOnClickListener(R.id.default_address_container)

        holder.getView<TextView>(R.id.tvChecked).isSelected = item.isDefaultAddress
        holder.getView<TextView>(R.id.tvDesc).isSelected = item.isDefaultAddress
    }
}