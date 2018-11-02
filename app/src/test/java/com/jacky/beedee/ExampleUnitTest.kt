package com.jacky.beedee

import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

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
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        val observable = Observable.interval(1, TimeUnit.SECONDS)


        observable.subscribe {
            Util.log("A-" + it)
        }


        scheduledExecutorService.schedule({
            observable.subscribe {
                Util.log("B-" + it)
            }
        }, 3, TimeUnit.SECONDS)


        Util.suspend(100)
    }
}
