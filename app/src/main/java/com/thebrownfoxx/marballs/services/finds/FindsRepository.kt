package com.thebrownfoxx.marballs.services.finds

import com.thebrownfoxx.marballs.domain.Find
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.flow.StateFlow

interface FindsRepository {
    val finds: StateFlow<List<Find>?>
    suspend fun updateFinds()
    suspend fun addFind(find: Find): Outcome<Unit>
    suspend fun removeFind(cacheId: String): Outcome<Unit>
}