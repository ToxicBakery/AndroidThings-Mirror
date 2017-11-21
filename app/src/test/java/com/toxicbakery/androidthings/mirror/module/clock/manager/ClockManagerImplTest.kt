package com.toxicbakery.androidthings.mirror.module.clock.manager

import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test

class ClockManagerImplTest {

    private val clockManager = ClockManagerImpl()

    @Test
    fun getDateTime() {
        clockManager.getDateTime()
                .subscribe { assertNotNull(it) }
    }

    companion object {
        @BeforeClass
        fun setup() {
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        }
    }

}