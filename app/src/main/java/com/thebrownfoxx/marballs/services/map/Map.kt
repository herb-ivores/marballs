package com.thebrownfoxx.marballs.services.map

import com.thebrownfoxx.marballs.domain.Location
import kotlinx.coroutines.flow.StateFlow

interface Map {
    val currentLocation: StateFlow<Location?>
    fun updateLocation()
}