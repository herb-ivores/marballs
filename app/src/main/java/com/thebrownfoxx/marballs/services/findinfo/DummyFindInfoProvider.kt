package com.thebrownfoxx.marballs.services.findinfo

import com.thebrownfoxx.marballs.domain.Find
import com.thebrownfoxx.marballs.domain.FindInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.services.cacheinfo.CacheInfoProvider
import com.thebrownfoxx.marballs.services.caches.CacheRepository
import java.time.Instant
import kotlin.random.Random

class DummyFindInfoProvider(
    private val cacheRepository: CacheRepository,
    private val cacheInfoProvider: CacheInfoProvider,
): FindInfoProvider {
    override fun Find.toFindInfo(): FindInfo {
        return FindInfo(
            id = id ?: Random.nextDouble().toString(),
            cache = with(cacheInfoProvider) {
                cacheRepository.caches.value?.first { it.id == cacheId }
                    ?.toCacheInfo(Location(0.0, 0.0))!!
            },
            found = Instant.now(),
            finder = User("ufhe", "fiuehfi"),
        )
    }
}