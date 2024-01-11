package com.thebrownfoxx.marballs.services.caches

import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.flow.StateFlow

interface CacheRepository {
    val caches: StateFlow<List<Cache>?>
    suspend fun getCache(cacheId: String): Outcome<Cache?>
    suspend fun addCache(cache: Cache): Outcome<Unit>
    suspend fun updateCache(cache: Cache): Outcome<Unit>
    suspend fun removeCache(cache: Cache): Outcome<Unit>
}