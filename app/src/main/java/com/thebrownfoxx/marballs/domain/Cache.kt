package com.thebrownfoxx.marballs.domain

data class Cache(
    val id: Long,
    val name: String,
    val description: String,
    val location: Location,
)
