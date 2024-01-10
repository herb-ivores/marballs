package com.thebrownfoxx.marballs.domain

data class Find(
    val id: String? = null,
    val cacheId: String,
    val finderId: String,
    val foundEpochMillis: Long,
)