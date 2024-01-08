package com.thebrownfoxx.marballs.ui.screens.home

import androidx.lifecycle.ViewModel
import com.thebrownfoxx.marballs.services.authentication.Authentication
import com.thebrownfoxx.marballs.services.map.Map

class HomeViewModel(
    private val authentication: Authentication,
    private val map: Map,
): ViewModel() {
    val loggedIn = authentication.loggedIn

    val currentLocation = map.currentLocation

    init {
        map.updateLocation()
    }

    fun logout() {
        authentication.logout()
    }
}