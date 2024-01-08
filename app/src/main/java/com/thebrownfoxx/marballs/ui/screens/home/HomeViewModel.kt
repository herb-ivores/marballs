package com.thebrownfoxx.marballs.ui.screens.home

import androidx.lifecycle.ViewModel
import com.thebrownfoxx.marballs.services.authentication.Authentication

class HomeViewModel(private val authentication: Authentication): ViewModel() {
    val loggedIn = authentication.loggedIn

    fun logout() {
        authentication.logout()
    }
}