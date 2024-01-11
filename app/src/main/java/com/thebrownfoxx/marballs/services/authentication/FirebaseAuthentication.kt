package com.thebrownfoxx.marballs.services.authentication

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.thebrownfoxx.extensions.mapToStateFlow
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.services.awaitOutcome
import com.thebrownfoxx.marballs.services.awaitUnitOutcome
import com.thebrownfoxx.marballs.services.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class FirebaseAuthentication(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository,
) : Authentication {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser = _currentUser.asStateFlow()

    override val loggedIn = currentUser.mapToStateFlow(scope = scope) { it != null }

    init {
        updateCurrentUser()
    }

    private fun updateCurrentUser() {
        _currentUser.value = auth.currentUser?.let {
            User(
                uid = it.uid,
                email = it.email ?: "",
            )
        }
    }

    override suspend fun signup(email: String, password: String): Outcome<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext auth.createUserWithEmailAndPassword(email, password)
                .awaitOutcome()
                .also { outcome ->
                    if (outcome is Outcome.Success) {
                        userRepository.addUser(
                            User(
                                uid = outcome.data.user?.uid ?: "",
                                email = email,
                            )
                        )
                    }
                }.map { }
        }

    override suspend fun login(email: String, password: String): Outcome<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext auth.signInWithEmailAndPassword(email, password)
                .awaitUnitOutcome()
                .also { updateCurrentUser() }
        }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
    ): Outcome<Unit> = withContext(Dispatchers.IO) {
        val currentUser = auth.currentUser
            ?: return@withContext Outcome.Failure(Exception("Not logged in"))
        val credential = EmailAuthProvider.getCredential(currentUser.email ?: "", oldPassword)
        if (currentUser.reauthenticate(credential).awaitUnitOutcome() is Outcome.Failure) {
            return@withContext Outcome.Failure(Exception("Could not authenticate user"))
        }
        currentUser.updatePassword(newPassword).awaitUnitOutcome()
    }

    override fun logout() {
        auth.signOut()
        updateCurrentUser()
    }
}