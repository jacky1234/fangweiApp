package com.jacky.beedee

import com.jacky.beedee.support.log.Logger

/**
 * 2018/10/30.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
class Test {
    init {
        io.reactivex.Observable.just(1)
                .subscribe({ integer -> assert(integer == 2) }, { throwable -> Logger.e(throwable) }) { Logger.i("complte") }
    }
}
