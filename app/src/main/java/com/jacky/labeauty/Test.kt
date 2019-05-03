package com.jacky.labeauty

import android.content.Intent

class Test {

    internal fun test(b: Boolean) {
        val intent = Intent()
        intent.putExtra("123", b)
    }

}
