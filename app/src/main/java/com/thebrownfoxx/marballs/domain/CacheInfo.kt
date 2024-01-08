package com.thebrownfoxx.marballs.domain

data class CacheInfo(
    val id: String,
    val name: String,
    val description: String,
    val location: String,
    val distance: Distance,
)