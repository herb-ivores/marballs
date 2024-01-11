package com.thebrownfoxx.marballs.services.user

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.StateFlow

class FireBaseUserRepository(private val firestore: FirebaseFirestore) : UserRepository {
    override val users: StateFlow<List<User>?>
        get() = TODO("Not yet implemented")
}