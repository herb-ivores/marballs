package com.thebrownfoxx.marballs.services.finds

import com.thebrownfoxx.marballs.domain.Find
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class DummyFindsRepository : FindsRepository {
    private val _finds = MutableStateFlow(emptyList<Find>())
    override val finds = _finds.asStateFlow()
    override suspend fun updateFinds() {}

    override suspend fun addFind(find: Find): Outcome<Unit> {
        _finds.update { it + find.copy(id = Random.nextDouble().toString()) }
        return Outcome.Success()
    }

    override suspend fun removeFind(cacheId: String): Outcome<Unit> {
        var outcome: Outcome<Unit> = Outcome.Success()
        val oldFind = _finds.value.firstOrNull { it.cacheId == cacheId }
        if (oldFind != null) {
            _finds.update { finds ->
                finds - oldFind
            }
        } else {
            outcome = Outcome.Failure(IllegalArgumentException("Cache ID not found"))
        }
        return outcome
    }
}