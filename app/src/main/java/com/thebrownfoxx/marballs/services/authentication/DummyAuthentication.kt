package com.thebrownfoxx.marballs.services.authentication

import com.thebrownfoxx.extensions.mapToStateFlow
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DummyAuthentication : Authentication {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser = _currentUser.asStateFlow()

    override val loggedIn = currentUser.mapToStateFlow(scope = scope) { it != null }

    override fun signup(
        email: String,
        password: String,
        onOutcomeReceived: (Outcome<Unit>) -> Unit,
    ) {
        onOutcomeReceived(Outcome.Success())
    }

    override fun login(
        email: String,
        password: String,
        onOutcomeReceived: (Outcome<Unit>) -> Unit,
    ) {
        _currentUser.value = User(
            uid = "123",
            email = email,
        )
        onOutcomeReceived(Outcome.Success())
    }

    override fun logout() {
        _currentUser.value = null
    }
}