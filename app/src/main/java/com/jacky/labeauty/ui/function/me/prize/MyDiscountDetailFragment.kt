package com.jacky.labeauty.ui.function.me.prize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.module.MyDiscount
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import kotlinx.android.synthetic.main.fragment_my_discount_detail.*

class MyDiscountDetailFragment : MySupportFragment() {
    lateinit var discount: MyDiscount

    companion object {
        const val KEY_DISCOUNT = "KEY_DISCOUNT"

        @JvmStatic
        fun newInstance(discount: MyDiscount): MySupportFragment {
            val fragment = MyDiscountDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_DISCOUNT, discount)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        discount = arguments?.getSerializable(KEY_DISCOUNT) as MyDiscount
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_discount_detail, container, false)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)


        titleView.setLeftAction(View.OnClickListener {
            pop()
        })

        tv_name.text = discount.name

        when (discount.reduceRule) {
            MyDiscount.VALUE -> {
                tv_discount_number.text = discount.reduceValue.toString()
                tvDiscountCate.text = "元"
            }
            MyDiscount.DISCOUNT -> {
                tv_discount_number.text = discount.reduceDiscount.toString()
                tvDiscountCate.text = "折"
            }
        }

        tv_scope.text = discount.desc
        tvInstructions.text = discount.instructions
    }
}