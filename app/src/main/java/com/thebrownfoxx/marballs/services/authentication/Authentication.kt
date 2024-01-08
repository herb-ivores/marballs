package com.thebrownfoxx.marballs.services.authentication

import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User
import kotlinx.coroutines.flow.StateFlow

interface Authentication {
    val currentUser: StateFlow<User?>
    val loggedIn: StateFlow<Boolean>
    fun signup(email: String, password: String, onOutcomeReceived: (Outcome<Unit>) -> Unit)
    fun login(email: String, password: String, onOutcomeReceived: (Outcome<Unit>) -> Unit)
    fun logout()
}