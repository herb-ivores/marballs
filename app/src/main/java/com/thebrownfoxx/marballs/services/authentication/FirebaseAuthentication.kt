package com.thebrownfoxx.marballs.services.authentication

import com.google.firebase.auth.FirebaseAuth
import com.thebrownfoxx.extensions.mapToStateFlow
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.services.addOnResultListener
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
        val firebaseUser = auth.currentUser
        _currentUser.value = User(
            uid = firebaseUser?.uid ?: "",
            email = firebaseUser?.email ?: "",
        )
    }

    override fun signup(email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnResultListener {
            onResult(it)
        }
    }

    override fun login(email: String, password: String, onResult: (Result<Unit>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnResultListener {
            onResult(it)
            updateCurrentUser()
        }
    }

    override fun logout() {
        auth.signOut()
        updateCurrentUser()
    }
}