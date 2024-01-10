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

    override fun addFind(find: Find, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        _finds.update { it + find.copy(id = Random.nextDouble().toString()) }
        onOutcomeReceived(Outcome.Success(Unit))
    }

    override fun removeFind(cacheId: String, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        val oldFind = _finds.value.firstOrNull { it.cacheId == cacheId }
        if (oldFind != null) {
            _finds.update { finds ->
                onOutcomeReceived(Outcome.Success(Unit))
                finds - oldFind
            }
            onOutcomeReceived(Outcome.Success(Unit))
        } else {
            onOutcomeReceived(Outcome.Failure(IllegalArgumentException("Find not found")))
        }
    }
}