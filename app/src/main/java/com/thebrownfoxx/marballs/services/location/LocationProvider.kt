package com.thebrownfoxx.marballs.services.location

import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.flow.StateFlow

interface LocationProvider {
    val currentLocation: StateFlow<Location?>
    suspend fun updateLocation(): Outcome<Unit>
}