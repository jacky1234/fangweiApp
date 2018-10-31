package com.jacky.beedee.logic.entity

import com.jacky.beedee.support.util.Strings

class MySelf private constructor() {
    var authorization: String
    var id: String
    var username: String
    var mobile: String
    var role: String

    init {
        authorization = Strings.empty
        id = Strings.empty
        username = Strings.empty
        mobile = Strings.empty
        role = Strings.empty
    }

    companion object {
        @JvmStatic
        fun get() = instance

        var instance = MySelf()
    }

    fun clear() {
        instance = MySelf()
    }

}
