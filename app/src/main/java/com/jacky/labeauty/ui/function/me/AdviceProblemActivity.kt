package com.jacky.labeauty.ui.function.me

import android.os.Bundle
import android.view.View
import com.jacky.labeauty.R
import com.jacky.labeauty.logic.entity.request.FeedbackRequest
import com.jacky.labeauty.logic.network.RequestHelper
import com.jacky.labeauty.support.ext.clickWithTrigger
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.support.util.Checker
import com.jacky.labeauty.support.util.Strings
import com.jacky.labeauty.ui.dialog.DialogTipsHelper
import com.jacky.labeauty.ui.inner.arch.BaseActivity
import com.jacky.labeauty.ui.widget.FeedbackItemView
import kotlinx.android.synthetic.main.activity_advice_problem.*

/**
 * 投诉建议
 */
class AdviceProblemActivity : BaseActivity() {
    private val itemViews = ArrayList<FeedbackItemView>(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice_problem)


        itemViews.add(itemFunctionException)
        itemViews.add(itemAdvice)
        itemViews.add(itemFunctionRequest)

        titleView.setLeftAction(View.OnClickListener { finish() })

        itemViews.forEach { it ->
            it.setCheckListener(object : FeedbackItemView.OnCheckListener {
                override fun onChecked(view: FeedbackItemView) {
                    onCheckedChanged(view)
                }
            })

            it.setOnClickListener {
                (it as FeedbackItemView).toggleChecked()
            }
        }

        tvConfirm.clickWithTrigger {
            val text = etContent.text.toString()
            if (Strings.isNullOrEmpty(text)) {
                AndroidUtil.toast("请输入内容")
                return@clickWithTrigger
            }

            var item: FeedbackItemView? = null
            for (itemView in itemViews) {
                if (itemView.isChecked()) {
                    item = itemView
                    break
                }
            }

            if (item == null) {
                AndroidUtil.toast("请选择反馈类型")
                return@clickWithTrigger
            }

            val phone = etPhone.text.toString()
            if (Strings.isNotBlank(phone) && !Checker.checkMobile(etPhone)) {
                return@clickWithTrigger
            }


            val request = FeedbackRequest()
            request.category = item.getCategory() as String?
            request.content = text
            request.contact = phone

            RequestHelper.get()
                    .feedbackProblem(request)
                    .compose(bindToDestroy())
                    .subscribe {
                        val createFeedbackDialog = DialogTipsHelper
                                .createFeedbackDialog(this)
                        createFeedbackDialog.setOnCancelListener {
                            this@AdviceProblemActivity.finish()
                        }
                        createFeedbackDialog.show()
                    }
        }
    }

    private fun onCheckedChanged(view: FeedbackItemView) {
        itemViews.forEach {
            if (view != it) {
                it.setChecked(false)
            }
        }
    }
}