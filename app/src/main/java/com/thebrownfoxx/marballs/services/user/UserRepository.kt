package com.thebrownfoxx.marballs.services.user

import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val users: StateFlow<List<User>?>
    suspend fun addUser(user: User): Outcome<Unit>
    suspend fun getUserById(userId: String): Outcome<User>
}