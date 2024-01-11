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
    override suspend fun getCache(cacheId: String): Outcome<Cache?> {
        return Outcome.Success(
            _caches.map { caches -> caches.find { it.id == cacheId } }.firstOrNull()
        )
    }

    override suspend fun addCache(cache: Cache): Outcome<Unit> {
        _caches.update { it + cache.copy(id = Random.nextDouble().toString()) }
        return Outcome.Success()
    }

    override suspend fun updateCache(cache: Cache): Outcome<Unit> {
        var outcome: Outcome<Unit> = Outcome.Success()
        _caches.update { caches ->
            val oldCache = caches.firstOrNull { it.id == cache.id }
            if (oldCache != null) {
                caches - oldCache + cache
            } else {
                outcome = Outcome.Failure(IllegalArgumentException("Cache not found"))
                caches
            }
        }
        return outcome
    }

    override suspend fun removeCache(cache: Cache): Outcome<Unit> {
        var outcome: Outcome<Unit> = Outcome.Success()
        _caches.update { caches ->
            val oldCache = caches.firstOrNull { it.id == cache.id }
            if (oldCache != null) {
                caches - oldCache
            } else {
                outcome = Outcome.Failure(IllegalArgumentException("Cache not found"))
                caches
            }
        }
        return outcome
    }
}