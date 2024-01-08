package com.thebrownfoxx.marballs.services.caches

import com.google.firebase.firestore.FirebaseFirestore
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Outcome
import kotlinx.coroutines.flow.StateFlow

class FirestoreCacheRepository(private val firestore: FirebaseFirestore) : CacheRepository {
    override val caches: StateFlow<List<Cache>>
        get() = TODO("Not yet implemented")

    override fun addCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun removeCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        TODO("Not yet implemented")
    }
}