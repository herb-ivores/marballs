package com.thebrownfoxx.marballs.services.auth

import kotlinx.coroutines.flow.StateFlow

interface AuthService {
    val loggedIn: StateFlow<Boolean>
    fun login(email: String, password: String, onResult: (Result<Unit>) -> Unit)
    fun signup(email: String, password: String, onResult: (Result<Unit>) -> Unit)
    fun logout()
}