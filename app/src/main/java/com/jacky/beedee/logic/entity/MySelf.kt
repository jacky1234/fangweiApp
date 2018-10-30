package com.jacky.beedee.logic.entity

import com.jacky.beedee.support.util.Strings

class MySelf private constructor() {
    var name: String = Strings.empty
    var authorization = Strings.empty

    companion object {
        val instance = MySelf()
    }

    fun clear() {

    }

}
