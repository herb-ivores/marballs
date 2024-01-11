package com.thebrownfoxx.marballs.services.authentication

import com.google.firebase.auth.FirebaseAuth
import com.thebrownfoxx.extensions.mapToStateFlow
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.services.awaitUnitOutcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FirebaseAuthentication(private val auth: FirebaseAuth) : Authentication {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser = _currentUser.asStateFlow()

    override val loggedIn = currentUser.mapToStateFlow(scope = scope) { it != null }

    private fun updateCurrentUser() {
        _currentUser.value = auth.currentUser?.let {
            User(
                uid = it.uid,
                email = it.email ?: "",
            )
        }
    }

    override suspend fun signup(email: String, password: String): Outcome<Unit> {
        return auth.createUserWithEmailAndPassword(email, password).awaitUnitOutcome()
    }

    override suspend fun login(email: String, password: String): Outcome<Unit> {
        return auth.signInWithEmailAndPassword(email, password).awaitUnitOutcome()
    }

    override fun logout() {
        auth.signOut()
        updateCurrentUser()
    }
}