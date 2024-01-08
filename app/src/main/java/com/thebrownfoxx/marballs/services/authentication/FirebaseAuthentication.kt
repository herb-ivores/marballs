package com.thebrownfoxx.marballs.services.authentication

import com.google.firebase.auth.FirebaseAuth
import com.thebrownfoxx.extensions.mapToStateFlow
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.services.addOnOutcomeListener
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

    override fun signup(email: String, password: String, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnOutcomeListener(onOutcomeReceived)
    }

    override fun login(email: String, password: String, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnOutcomeListener {
            onOutcomeReceived(it)
            updateCurrentUser()
        }
    }

    override fun logout() {
        auth.signOut()
        updateCurrentUser()
    }
}