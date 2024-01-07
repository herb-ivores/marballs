package com.thebrownfoxx.marballs.services.auth

import com.google.firebase.auth.FirebaseAuth
import com.thebrownfoxx.marballs.services.addOnResultListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FirebaseAuthService(private val auth: FirebaseAuth): AuthService {
    private val _loggedIn = MutableStateFlow(false)
    override val loggedIn = _loggedIn.asStateFlow()

    private fun updateLoggedIn() {
        _loggedIn.value = auth.currentUser != null
    }

    override fun login(email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnResultListener {
            onResult(it)
            updateLoggedIn()
        }
    }

    override fun signup(email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnResultListener {
            onResult(it)
            updateLoggedIn()
        }
    }

    override fun logout() {
        auth.signOut()
        updateLoggedIn()
    }
}