package com.jacky.beedee.logic.entity

import com.jacky.beedee.logic.DaoFacade
import com.jacky.beedee.support.util.Strings

class MySelf private constructor() {
    var authorization: String? = null
    var id: String? = null
    var username: String? = null
    var mobile: String? = null
    var role: String? = null

    init {
        val dataFromDb = DaoFacade.get().mySelfInfo
        if (dataFromDb != null) {
            authorization = dataFromDb.authorization
            id = dataFromDb.id
            username = dataFromDb.username
            mobile = dataFromDb.mobile
            role = dataFromDb.role
        }
    }

    companion object {
        @JvmStatic
        fun get() = instance

        var instance = MySelf()
    }

    fun isLogined(): Boolean {
        return Strings.isNotBlank(id)
    }

    fun clear() {
        DaoFacade.get().saveMyselfInfo(null)
        instance = MySelf()
    }

}
