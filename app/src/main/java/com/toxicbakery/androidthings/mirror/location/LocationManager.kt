package com.toxicbakery.androidthings.mirror.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.github.salomonbrys.kodein.*
import com.google.android.gms.location.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import timber.log.Timber

/*
 Unfortunately Android Things does not implement play services backed location lookup :(
 A way around this is to use the location service directly and provide a gps device however that does
 not make sense for this project which is intended to be a stationary display.
  */
@SuppressLint("MissingPermission")
class LocationManagerImpl(
        private val settingsClient: SettingsClient,
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        private val locationCallbackFactory: (Pair<ObservableEmitter<LocationResult>, FusedLocationProviderClient>) -> LocationCallback
) : LocationManager {

    private val locationRequest: LocationRequest = LocationRequest().apply {
        interval = 5000L
        fastestInterval = 5000L
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun getLocation(): Observable<LocationResult> {
        return Observable.create<LocationResult> { emitter: ObservableEmitter<LocationResult> ->
            val locationCallback = locationCallbackFactory(Pair(emitter, fusedLocationProviderClient))
            LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)
                    .build()
                    .let { settingsClient.checkLocationSettings(it) }
                    .addOnSuccessListener {
                        fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest, locationCallback, Looper.myLooper())
                    }
                    .addOnFailureListener { emitter.onError(LocationException("Failed to init", it)) }
        }
    }
}

internal class RxLocationCallback(
        private val emitter: ObservableEmitter<LocationResult>,
        private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationCallback() {
    override fun onLocationResult(locationResult: LocationResult) {
        if (emitter.isDisposed) fusedLocationProviderClient.removeLocationUpdates(this)
        else emitter.onNext(locationResult)
    }

    override fun onLocationAvailability(locationAvailability: LocationAvailability) {
        Timber.d("isLocationAvailable: %s", locationAvailability.isLocationAvailable)
    }
}

interface LocationManager {

    /**
     * Returns an observable for the current location and future location changes.
     */
    fun getLocation(): Observable<LocationResult>
}

val locationModule = Kodein.Module {
    bind<SettingsClient>() with provider { LocationServices.getSettingsClient(instance<Context>()) }
    bind<FusedLocationProviderClient>() with provider {
        LocationServices.getFusedLocationProviderClient(instance<Context>())
    }
    bind<LocationCallback>() with factory { params: Pair<ObservableEmitter<LocationResult>, FusedLocationProviderClient> ->
        params.let { (emitter, fusedLocationProviderClient) -> RxLocationCallback(emitter, fusedLocationProviderClient) }
    }
    bind<LocationManager>() with provider { LocationManagerImpl(instance(), instance(), factory()) }
}