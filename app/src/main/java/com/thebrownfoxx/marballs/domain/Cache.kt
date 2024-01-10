package com.thebrownfoxx.marballs.domain

data class Cache(
    val id: String? = null,
    val name: String,
    val description: String,
    val location: Location,
    val authorUid: String,
)
