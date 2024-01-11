package com.thebrownfoxx.marballs.services.location

import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DummyLocationProvider: LocationProvider {
    private val _currentLocation = MutableStateFlow(Location(0.0, 0.0))
    override val currentLocation = _currentLocation.asStateFlow()

    override suspend fun updateLocation(): Outcome<Unit> {
        _currentLocation.value = Location(15.1454993, 120.5923863)
        return Outcome.Success(Unit)
    }
}