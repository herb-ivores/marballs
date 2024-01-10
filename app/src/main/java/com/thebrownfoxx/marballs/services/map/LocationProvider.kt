package com.thebrownfoxx.marballs.services.map

import com.thebrownfoxx.marballs.domain.Location
import kotlinx.coroutines.flow.StateFlow

interface LocationProvider {
    val currentLocation: StateFlow<Location?>
    fun updateLocation()
}