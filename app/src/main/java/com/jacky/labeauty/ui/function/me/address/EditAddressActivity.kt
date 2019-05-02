package com.jacky.labeauty.ui.function.me.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Address
import com.jacky.labeauty.logic.entity.module.MySelf
import com.jacky.labeauty.logic.entity.request.AddAddressRequest
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.function.Predicate
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.Checker
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.smarttop.library.bean.City
import com.smarttop.library.bean.County
import com.smarttop.library.bean.Province
import com.smarttop.library.bean.Street
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.BottomDialog
import com.smarttop.library.widget.OnAddressSelectedListener
import kotlinx.android.synthetic.main.activity_edit_address.*


class EditAddressActivity : BaseActivity(), OnAddressSelectedListener
        , AddressSelector.OnDialogCloseListener
        , AddressSelector.onSelectorAreaPositionListener {
    var dialog: BottomDialog? = null
    private var isSelectAddress = false

    companion object {
        const val KEY_ADDRESS = "KEY_ADDRESS"

        @JvmStatic
        fun launch(from: Activity, address: Address? = null) {
            val intent = Intent(from, EditAddressActivity::class.java)
            if (address != null) {
                intent.putExtra(KEY_ADDRESS, address)
            }
            from.startActivity(intent)
        }
    }

    override fun onAddressSelected(province: Province?, city: City?, county: County?, street: Street?) {
//        val provinceCode = if (province == null) "" else province.code
//        val cityCode = if (city == null) "" else city.code
//        val countyCode = if (county == null) "" else county.code
//        val streetCode = if (street == null) "" else street.code
//        Log.d("EditAddressActivity", "省份id=$provinceCode")
//        Log.d("EditAddressActivity", "城市id=$cityCode")
//        Log.d("EditAddressActivity", "乡镇id=$countyCode")
//        Log.d("EditAddressActivity", "街道id=$streetCode")
//        val s = (if (province == null) "" else province.name) + (if (city == null) "" else city.name) + (if (county == null) "" else county.name) +
//                if (street == null) "" else street.name

        isSelectAddress = true
        onChooseResult(province?.name, city?.name, county?.name, street?.name)
        dialog?.dismiss()
    }

    private fun onChooseResult(province: String?, city: String?, county: String?, street: String?) {
        tvProvince.text = province ?: ""
        tvShi.text = city ?: ""
        tvXian.text = county ?: ""
        etAddressDetail.setText(street ?: "")
    }

    override fun dialogclose() {
        try {
            dialog?.dismiss()
        } catch (e: Exception) {
        }
    }

    override fun selectorAreaPosition(provincePosition: Int, cityPosition: Int, countyPosition: Int, streetPosition: Int) {
        Log.d("EditAddressActivity", "selectorAreaPosition")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)

        etName.setText(MySelf.get().nickName)
        titleView.setLeftAction(View.OnClickListener { finish() })

        tvProvince.setOnClickListener { showChooseAddressPanel() }
        tvShi.setOnClickListener { showChooseAddressPanel() }
        tvXian.setOnClickListener { showChooseAddressPanel() }

        fillWithAddress(intent.getSerializableExtra(KEY_ADDRESS) as Address?)
        tvConfirm.setOnClickListener {
            if ((Checker.check(etName, "请输入收货人姓名")
                            && Checker.checkMobile(etPhone)
                            &&
                            Checker.check(Predicate { t -> t }, isSelectAddress, "请选择 省/市/区")
                            && Checker.check(etAddressDetail, "请输入详细地址"))) {
                val name = etName.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val province = tvProvince.text.toString()
                val city = tvShi.text.toString()
                val county = tvXian.text.toString()
                val detail = etAddressDetail.text.toString().trim()

                val request = AddAddressRequest()
                request.province = province
                request.city = city
                request.area = county
                request.address = detail
                request.name = name
                request.mobile = phone
                request.isDefaultAddress = true

                RequestHelper.get().addAddressRecorder(request)
                        .compose(bindToDestroy())
                        .subscribe {
                            //add success
                            AndroidUtil.toast("添加成功")
                            finish()
                        }
            }
        }
    }

    private fun fillWithAddress(address: Address?) {
        if (address != null) {

        }
    }

    private fun showChooseAddressPanel() {
        val dialog = BottomDialog(this)
        val window = dialog.window
        val attributes = window.attributes
        attributes.height = (AndroidUtil.getScreenHeight() * 3.0f / 4).toInt()
        window?.attributes = attributes

        dialog.setTextSelectedColor(R.color.labe_grey)
        dialog.setTextUnSelectedColor(R.color.labe_blue)
        dialog.setIndicatorBackgroundColor(R.color.labe_blue)

        dialog.setOnAddressSelectedListener(this)
        dialog.setDialogDismisListener(this)
        dialog.setSelectorAreaPositionListener(this)
        dialog.setOnDismissListener {
            this@EditAddressActivity.dialog = null
        }
        dialog.show()

        this@EditAddressActivity.dialog = dialog
    }
}
