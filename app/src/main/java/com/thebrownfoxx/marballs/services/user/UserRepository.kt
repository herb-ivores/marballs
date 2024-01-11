package com.thebrownfoxx.marballs.services.user

import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val users: StateFlow<List<User>?>
}