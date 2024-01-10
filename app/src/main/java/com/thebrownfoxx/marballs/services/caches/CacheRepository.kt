package com.thebrownfoxx.marballs.services.caches

import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.flow.StateFlow

interface CacheRepository {
    val caches: StateFlow<List<Cache>?>
    suspend fun getCache(cacheId: String) : Cache?
    fun addCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit)
    fun updateCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit)
    fun removeCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit)
}