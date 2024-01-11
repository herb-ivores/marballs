package com.thebrownfoxx.marballs.services.user

import com.google.firebase.firestore.FirebaseFirestore

import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.services.awaitUnitOutcome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class FireBaseUserRepository(private val firestore: FirebaseFirestore) : UserRepository {

    private val _users = MutableStateFlow<List<User>?>(null)
    override val users: StateFlow<List<User>?> = _users.asStateFlow()

    init {
        updateUsers()
    }

    fun updateUsers() {
        firestore.collection("users")
            .get()
            .addOnSuccessListener { results ->
                _users.value = results.map {
                   User(
                       uid = it.getString("uid") ?: "",
                       email = it.getString("email") ?: ""
                   )
                }
            }
    }
    override suspend fun addUser(user: User): Outcome<Unit> = withContext(Dispatchers.IO) {
        val userMap = mapOf(
            "uid" to user.uid,
            "email" to user.email
        )
        return@withContext firestore.collection("users")
            .add(userMap)
            .awaitUnitOutcome()
            .also { updateUsers() }
    }
}