package com.jacky.beedee.support.util

import android.widget.EditText
import com.jacky.beedee.support.Starter
import com.jacky.beedee.support.ext.toast

class Validate {
    companion object {
        @JvmStatic
        fun check(edit: EditText, char: CharSequence): Boolean {
            val nickname = edit.text.toString()
            if (Strings.isNullOrEmpty(nickname)) {
                Starter.getContext().toast(char)
                return false
            }

            return true
        }
    }
}