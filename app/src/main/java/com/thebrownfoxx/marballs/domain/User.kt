package com.thebrownfoxx.marballs.domain

data class User(
    val uid: String,
    val email: String,
) {
    val username = email.substringBefore('@')
}