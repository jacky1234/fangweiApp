package com.jacky.labeauty.ui.function.me.prize

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.Constant
import com.jacky.labeauty.logic.entity.module.Address
import com.jacky.labeauty.logic.entity.module.MyEntityPrize
import com.jacky.labeauty.logic.entity.module.Prize
import com.jacky.labeauty.logic.entity.module.PrizeLog
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.function.me.address.MyAddressActivity
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_extract_prize.*

class ExtractPrizeActivity : BaseActivity() {
    var prize: Prize? = null
    var prizeLog: PrizeLog? = null
    var myEntityPrize: MyEntityPrize? = null
    private var addressId: String? = null

    companion object {
        const val KEY_PRIZE = "KEY_PRIZE"
        const val KEY_PRIZE_LOG = "KEY_PRIZE_LOG"
        const val KEY_ENTITY_PRIZE = "KEY_ENTITY_PRIZE"

        @JvmStatic
        fun launch(from: Activity, prize: Prize, prizeLog: PrizeLog) {
            val intent = Intent(from, ExtractPrizeActivity::class.java)
            intent.putExtra(KEY_PRIZE, prize)
            intent.putExtra(KEY_PRIZE_LOG, prizeLog)
            from.startActivity(intent)
        }

        @JvmStatic
        fun launch(from: Activity, prize: MyEntityPrize) {
            val intent = Intent(from, ExtractPrizeActivity::class.java)
            intent.putExtra(KEY_ENTITY_PRIZE, prize)
            from.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract_prize)
        prize = intent.getSerializableExtra(KEY_PRIZE) as Prize?
        prizeLog = intent.getSerializableExtra(KEY_PRIZE_LOG) as PrizeLog?
        myEntityPrize = intent.getSerializableExtra(KEY_ENTITY_PRIZE) as MyEntityPrize?

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

            val logId = if (prizeLog != null) {
                prizeLog!!.id
            } else {
                myEntityPrize!!.prizeLogId
            }
            RequestHelper.get().bindAddress(logId, addressId)
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
        val prize = prize
        val prizeLog = prizeLog
        val myEntityPrize = myEntityPrize
        if (prize != null && prizeLog != null) {
            tv_prize_name.text = prize.name
            tv_date.text = getDate(prizeLog.createTime, prizeLog.createTime + Constant.SEVEN_DAY_TM)
            Glide.with(this)
                    .load(prize.thumb)
                    .into(iv_prize_thumb)
        } else if (myEntityPrize != null) {
            tv_prize_name.text = myEntityPrize.name
            tv_date.text = getDate(myEntityPrize.sendTime, myEntityPrize.sendTime + Constant.SEVEN_DAY_TM)
            Glide.with(this)
                    .load(myEntityPrize.thumb)
                    .into(iv_prize_thumb)
        }
    }

    private fun getDate(start: Long, end: Long): CharSequence? {
        return formatTime(start).toString() + "-" + formatTime(end)
    }

    private fun formatTime(time: Long): CharSequence {
        return AndroidUtil.formatTime("yyyy.MM.dd", time)
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