package com.thebrownfoxx.marballs.services.auth

import com.thebrownfoxx.marballs.domain.User
import kotlinx.coroutines.flow.StateFlow

interface AuthService {
    val currentUser: StateFlow<User?>
    val loggedIn: StateFlow<Boolean>
    fun signup(email: String, password: String, onResult: (Result<Unit>) -> Unit)
    fun login(email: String, password: String, onResult: (Result<Unit>) -> Unit)
    fun logout()
}