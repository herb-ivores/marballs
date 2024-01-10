package com.thebrownfoxx.marballs.services.map

import com.thebrownfoxx.marballs.domain.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DummyLocationProvider: LocationProvider {
    private val _currentLocation = MutableStateFlow(Location(0.0, 0.0))
    override val currentLocation = _currentLocation.asStateFlow()

    override fun updateLocation() {
        _currentLocation.value = Location(15.1454993, 120.5923863)
    }
}