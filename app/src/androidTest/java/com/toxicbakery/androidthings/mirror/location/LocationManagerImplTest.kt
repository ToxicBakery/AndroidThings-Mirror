package com.toxicbakery.androidthings.mirror.location

import com.google.android.gms.location.*
import com.google.android.gms.tasks.Tasks
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.ObservableEmitter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocationManagerImplTest {

    private lateinit var settingsClient: SettingsClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallbackFactory: (params: Pair<ObservableEmitter<LocationResult>, FusedLocationProviderClient>) -> LocationCallback
    private lateinit var locationCallback: TestablelocationCallback
    private lateinit var locationManager: LocationManagerImpl

    @Before
    fun setup() {
        settingsClient = mock()
        fusedLocationProviderClient = mock()
        locationCallbackFactory = { (emitter, client) ->
            TestablelocationCallback(emitter, client).also { locationCallback = it }
        }
        locationManager = LocationManagerImpl(
                settingsClient,
                fusedLocationProviderClient,
                locationCallbackFactory)
    }

    @Test
    fun getLocation() {
        whenever(settingsClient.checkLocationSettings(any())).thenReturn(Tasks.forResult(LocationSettingsResponse()))
        locationManager.getLocation()
                .subscribe { locationResult: LocationResult ->
                    assertEquals(LocationResult.create(listOf()), locationResult)
                }

        locationCallback.apply {
            emitter.onNext(LocationResult.create(listOf()))
            emitter.onComplete()
        }
    }

    class TestablelocationCallback(
            val emitter: ObservableEmitter<LocationResult>,
            val fusedLocationProviderClient: FusedLocationProviderClient
    ) : LocationCallback()

}