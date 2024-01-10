package com.thebrownfoxx.marballs.services.caches

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.addOnOutcomeListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class FirestoreCacheRepository(private val firestore: FirebaseFirestore) : CacheRepository {
    private val _caches = MutableStateFlow<List<Cache>?>(null)
    override val caches = _caches.asStateFlow()

    init {
        fetchCaches()
    }

    private fun fetchCaches() {
        firestore.collection("tasks").snapshots().map { querySnapshot->
            querySnapshot.toObjects(Cache::class.java)
        }
    }
    override fun addCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {

        val cacheMap = mapOf(
            "id" to cache.id,
            "name" to cache.name,
            "description" to cache.description,
            "location" to mapOf(
                "latitude" to cache.location.latitude,
                "longitude" to cache.location.longitude
            )
        )

        firestore.collection("caches")
            .add(cacheMap)
            .addOnOutcomeListener(onOutcomeReceived)
    }

    override fun updateCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {

        val cacheMap = mapOf(
            "id" to cache.id,
            "name" to cache.name,
            "description" to cache.description,
            "location" to mapOf(
                "latitude" to cache.location.latitude,
                "longitude" to cache.location.longitude
            )
        )

        firestore.collection("caches").document(cache.id)
            .set(cacheMap)
            .addOnOutcomeListener(onOutcomeReceived)
    }

    override fun removeCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        firestore.collection("caches").document(cache.id)
            .delete()
            .addOnOutcomeListener(onOutcomeReceived)
    }
}