package com.thebrownfoxx.marballs.services.caches

import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class DummyCacheRepository: CacheRepository {
    private val _caches = MutableStateFlow(emptyList<Cache>())
    override val caches = _caches.asStateFlow()

    override fun addCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        _caches.update { it + cache.copy(id = Random.nextDouble().toString()) }
        onOutcomeReceived(Outcome.Success())
    }

    override suspend fun getCache(cacheId: String): Cache? {
       return _caches.map { it.find { it.id == cacheId } }.firstOrNull()
    }

    override fun updateCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        _caches.update { caches ->
            val oldCache = caches.firstOrNull { it.id == cache.id }
            if (oldCache != null) {
                onOutcomeReceived(Outcome.Success())
                caches - oldCache + cache
            } else {
                onOutcomeReceived(Outcome.Failure(IllegalArgumentException("Cache not found")))
                caches
            }
        }
    }

    override fun removeCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        _caches.update { caches ->
            val oldCache = caches.firstOrNull { it.id == cache.id }
            if (oldCache != null) {
                onOutcomeReceived(Outcome.Success())
                caches - oldCache
            } else {
                onOutcomeReceived(Outcome.Failure(IllegalArgumentException("Cache not found")))
                caches
            }
        }
    }
}