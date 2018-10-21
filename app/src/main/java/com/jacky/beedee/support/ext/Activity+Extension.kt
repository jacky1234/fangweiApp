package com.jacky.beedee.support.ext

import android.app.Activity
import android.content.Intent

/**
 * 2018/10/20.
 * GitHub:[https://github.com/jacky1234]
 * @author  jacky
 */

//start<DetailActivity>
inline fun <reified T : Activity> Activity?.start() {
    val intent = Intent(this, T::class.java)
    this?.startActivity(intent)
}