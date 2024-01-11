package com.thebrownfoxx.marballs.services.location

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.awaitOutcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GoogleLocationProvider(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val application: Application,
) : LocationProvider {
    private val _currentLocation = MutableStateFlow<Location?>(null)
    override val currentLocation = _currentLocation.asStateFlow()

    override suspend fun updateLocation(): Outcome<Unit> {
        if (
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Outcome.Failure(IllegalStateException("Location permissions not granted"))
        }
        return fusedLocationClient.lastLocation.awaitOutcome()
            .also {
                Log.d(this::class.simpleName, "updateLocation: $it")
                if (it is Outcome.Success) {
                    _currentLocation.value = Location(it.data.latitude, it.data.longitude)
                }
            }
            .map {  }
    }
}