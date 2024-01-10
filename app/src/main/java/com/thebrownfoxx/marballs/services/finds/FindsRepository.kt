package com.thebrownfoxx.marballs.services.finds

import com.thebrownfoxx.marballs.domain.Find
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.flow.StateFlow

interface FindsRepository {
    val finds: StateFlow<List<Find>?>
    fun addFind(find: Find, onOutcomeReceived: (Outcome<Unit>) -> Unit)
    fun removeFind(cacheId: String, onOutcomeReceived: (Outcome<Unit>) -> Unit)
}