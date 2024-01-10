package com.thebrownfoxx.marballs.services.location

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.thebrownfoxx.marballs.domain.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GoogleLocationProvider(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val application: Application,
) : LocationProvider {
    private val _currentLocation = MutableStateFlow<Location?>(null)
    override val currentLocation = _currentLocation.asStateFlow()

    override fun updateLocation() {
        if (
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            _currentLocation.value = Location(
                latitude = it.latitude,
                longitude = it.longitude,
            )
        }
    }
}