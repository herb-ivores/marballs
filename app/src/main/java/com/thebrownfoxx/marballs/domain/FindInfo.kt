package com.thebrownfoxx.marballs.domain

import java.time.Instant

data class FindInfo(
    val id: String,
    val cache: CacheInfo,
    val finder: User,
    val found: Instant,
)