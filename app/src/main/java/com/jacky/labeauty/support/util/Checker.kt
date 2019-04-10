package com.jacky.labeauty.support.util

import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.regex.RegexUtils

class Checker {
    companion object {
        @JvmStatic
        fun check(edit: EditText, toast: CharSequence): Boolean {
            val nickname = edit.text.toString()
            if (Strings.isNullOrEmpty(nickname)) {
                AndroidUtil.toast(toast)
                return false
            }

            return true
        }

        @JvmStatic
        fun equalsContent(first: TextView, second: TextView, toast: CharSequence): Boolean {
            if (!Strings.equals(first.text.toString(), second.text.toString())) {
                AndroidUtil.toast(toast)
                return false
            }

            return true
        }

        @JvmStatic
        fun checkMobile(textView: TextView): Boolean {
            val phone = textView.text.toString()
            if (!RegexUtils.isMobileSimple(phone)) {
                AndroidUtil.toast(R.string.mobile_number_wrong)
                return false
            }

            return true
        }

        @JvmStatic
        fun CheckChecked(checkBox: CheckBox, toast: CharSequence): Boolean {
            if (!checkBox.isChecked) {
                AndroidUtil.toast(toast)
                return false
            }

            return true
        }
    }
}