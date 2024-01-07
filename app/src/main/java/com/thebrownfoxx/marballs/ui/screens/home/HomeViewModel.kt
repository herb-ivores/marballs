package com.thebrownfoxx.marballs.ui.screens.home

import androidx.lifecycle.ViewModel
import com.thebrownfoxx.marballs.services.auth.AuthService

class HomeViewModel(private val authService: AuthService): ViewModel() {
    val loggedIn = authService.loggedIn

    fun logout() {
        authService.logout()
    }
}