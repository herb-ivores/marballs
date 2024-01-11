package com.thebrownfoxx.marballs.services.authentication

import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User
import kotlinx.coroutines.flow.StateFlow

interface Authentication {
    val currentUser: StateFlow<User?>
    val loggedIn: StateFlow<Boolean>
    suspend fun signup(email: String, password: String): Outcome<Unit>
    suspend fun login(email: String, password: String): Outcome<Unit>
    suspend fun changePassword(oldPassword: String, newPassword: String): Outcome<Unit>
    fun logout()
}