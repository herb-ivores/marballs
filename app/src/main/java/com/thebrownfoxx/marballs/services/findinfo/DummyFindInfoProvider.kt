package com.thebrownfoxx.marballs.services.findinfo

import com.thebrownfoxx.marballs.domain.Find
import com.thebrownfoxx.marballs.domain.FindInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.services.cacheinfo.CacheInfoProvider
import com.thebrownfoxx.marballs.services.caches.CacheRepository
import com.thebrownfoxx.marballs.services.user.UserRepository
import java.time.Instant
import kotlin.random.Random

class DummyFindInfoProvider(
    private val cacheRepository: CacheRepository,
    private val cacheInfoProvider: CacheInfoProvider,
    private val userRepository: UserRepository,
): FindInfoProvider {
    override suspend fun Find.toFindInfo(): Outcome<FindInfo> {
        val cache = with(cacheInfoProvider) {
            cacheRepository.caches.value?.firstOrNull { it.id == cacheId }
                ?.toCacheInfo(Location(0.0, 0.0))
        }

        return when (cache) {
            is Outcome.Success -> {
                Outcome.Success(
                    FindInfo(
                        id = id ?: Random.nextDouble().toString(),
                        cache = cache.data,
                        found = Instant.now(),
                        finder = User("ufhe", "fiuehfi"),
                    )
                )
            }
            is Outcome.Failure -> {
                Outcome.Failure(cache.throwable)
            }
            null -> Outcome.Failure(IllegalStateException("Cache not found"))
        }
    }
}