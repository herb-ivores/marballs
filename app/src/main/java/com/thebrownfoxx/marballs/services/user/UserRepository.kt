package com.thebrownfoxx.marballs.services.user

import android.os.OutcomeReceiver
import com.google.firebase.firestore.auth.User
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val users: StateFlow<List<User>?>
    suspend fun addUser(user: User): Outcome<Unit>
}