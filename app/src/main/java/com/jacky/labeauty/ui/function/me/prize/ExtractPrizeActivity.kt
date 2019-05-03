package com.jacky.labeauty.ui.function.me.prize

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.Address
import com.jacky.labeauty.logic.entity.module.Prize
import com.jacky.labeauty.logic.entity.module.PrizeLog
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.function.me.address.MyAddressActivity
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_extract_prize.*

class ExtractPrizeActivity : BaseActivity() {
    lateinit var prize: Prize
    lateinit var prizeLog: PrizeLog
    private var addressId: String? = null

    companion object {
        const val KEY_PRIZE = "KEY_PRIZE"
        const val KEY_PRIZE_LOG = "KEY_PRIZE_LOG"

        @JvmStatic
        fun launch(from: Activity, prize: Prize, prizeLog: PrizeLog) {
            val intent = Intent(from, ExtractPrizeActivity::class.java)
            intent.putExtra(KEY_PRIZE, prize)
            intent.putExtra(KEY_PRIZE_LOG, prizeLog)
            from.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract_prize)
        prize = intent.getSerializableExtra(KEY_PRIZE) as Prize
        prizeLog = intent.getSerializableExtra(KEY_PRIZE_LOG) as PrizeLog

        titleView.setLeftAction(View.OnClickListener { finish() })
        requestAddresses()
        fillPrize()

        constraintLayout3.clickWithTrigger {
            MyAddressActivity.launch(this, true, MyAddressActivity.REQUEST_ADDRESS_CODE)
        }

        tvConfirm.clickWithTrigger {
            if (addressId == null) {
                AndroidUtil.toast("请选择收货地址")
                return@clickWithTrigger
            }

            RequestHelper.get().bindAddress(prizeLog.id, addressId)
                    .compose(bindToDestroy())
                    .subscribe {
                        finish()
                        ShowSuccessActivity.launch(this, ShowSuccessActivity.TYPE_PRIZE)
                    }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                MyAddressActivity.REQUEST_ADDRESS_CODE -> {
                    val address = data?.getSerializableExtra(MyAddressActivity.KEY_RESULT_ADDRESS) as Address?
                    if (address != null) {
                        handleAddress(address)
                    }
                }
            }
        }
    }

    private fun fillPrize() {
        tv_prize_name.text = prize.name
        tv_date.text = getDate()
        Glide.with(this)
                .load(prize.thumb)
                .into(iv_prize_thumb)
    }

    private fun getDate(): CharSequence? {
        return "2019.04.04-2019.05.03"
    }

    @SuppressLint("CheckResult")
    private fun requestAddresses() {
        RequestHelper.get()
                .requestAddresses(false)
                .compose(bindToDestroy())
                .subscribe {
                    for (address in it) {
                        if (address.isDefaultAddress) {
                            handleAddress(address)
                            break
                        }
                    }
                }
    }

    private fun handleAddress(address: Address) {
        addressId = address.id
        tv_name.text = address.name
        tv_mobile.text = address.mobile
        tvAddress.text = address.address
    }
}