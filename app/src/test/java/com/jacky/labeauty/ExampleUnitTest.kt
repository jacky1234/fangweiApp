package com.jacky.labeauty

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    lateinit var scheduledExecutorService: ScheduledExecutorService
    @Before
    fun ready() {
        scheduledExecutorService = Executors.newScheduledThreadPool(1)

    }

    @Test
    fun code_observable() {
        val observable = Observable.interval(1, TimeUnit.SECONDS)
                .take(20)
        observable.subscribe {
            Util.log("A-" + it)
        }


        scheduledExecutorService.schedule({
            observable.subscribe {
                Util.log("B-" + it)
            }
        }, 3, TimeUnit.SECONDS)


        Util.suspend(30)
    }

    @Test
    fun hot_observable() {
        val observable = Observable.interval(1, TimeUnit.SECONDS)            //normal connectObservable
                .take(10).publish()
//        val observable = Observable.interval(1, TimeUnit.SECONDS)       //replay
//                .take(20).replay()

        observable.subscribe {
            Util.log("A-" + it)
        }

        val dispose = observable.connect()
        scheduledExecutorService.schedule({
            observable.subscribe {
                Util.log("B-" + it)
            }
        }, 3, TimeUnit.SECONDS)

        //dispose after seconds
//        scheduledExecutorService.schedule({
//            dispose.dispose()
//        }, 10, TimeUnit.SECONDS)


        scheduledExecutorService.schedule({
            observable.subscribe {
                Util.log("C-" + it)
            }
        }, 5, TimeUnit.SECONDS)

        scheduledExecutorService.schedule({
            Util.log("observable disposeed? :" + dispose.isDisposed)

            observable.subscribe {
                observable.connect()
                Util.log("D-" + it)
            }
        }, 11, TimeUnit.SECONDS)

        Util.suspend(30)
    }

    @Test
    fun hold_dispose() {
        val observable = Observable.interval(1, TimeUnit.SECONDS)            //normal connectObservable
                .take(10).publish()

//        observable.subscribe {
//            Util.log("A-" + it)
//        }
        observable.doOnNext {
            Util.log("do on next:" + it)
        }
        val connect = observable.connect()
        scheduledExecutorService.schedule({
            Util.log("disposed 1?:" + connect.isDisposed)
        }, 1, TimeUnit.SECONDS)

        scheduledExecutorService.schedule({
            Util.log("disposed 2?:" + connect.isDisposed)
        }, 2, TimeUnit.SECONDS)

        scheduledExecutorService.schedule({
            Util.log("disposed 3?:" + connect.isDisposed)
        }, 11, TimeUnit.SECONDS)
        Util.suspend(20)
    }

    @Test
    fun regex() {
        val r_name3 = "https://static.labeauty.yituizhineng.top/201911/5dbd1d55e90cf8515b9e6c45_s2115207_w750_h10199.jpg"
        val pattern = Pattern.compile("w\\d+_h\\d+")
        val matcher = pattern.matcher(r_name3)
        if (matcher.find()) {
            val message = matcher.group()
            println(message)

            val split = message.split("_")
            if (split[0].startsWith("w")) {
                val w = split[0].trim().replace("w","")
                println(w)
            }

            if(split[1].startsWith("h")){
                val h = split[1].trim().replace("h","")
                println(h)
            }
        }
    }
}