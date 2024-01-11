package com.thebrownfoxx.marballs.services.caches

import com.google.firebase.firestore.FirebaseFirestore
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.awaitOutcome
import com.thebrownfoxx.marballs.services.awaitUnitOutcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirestoreCacheRepository(private val firestore: FirebaseFirestore) : CacheRepository {
    val scope = CoroutineScope(Dispatchers.IO)

    private val _caches = MutableStateFlow<List<Cache>?>(null)
    override val caches = _caches.asStateFlow()

    init {
        scope.launch {
            updateCaches()
        }
    }

    override suspend fun updateCaches() {
        firestore.collection("caches")
            .get()
            .addOnSuccessListener { results ->
                _caches.value = results.map {
                    Cache(
                        id = it.id,
                        name = it.getString("name") ?: "",
                        description = it.getString("description") ?: "",
                        location = Location(
                            it.getDouble("latitude") ?: 0.0,
                            it.getDouble("longitude") ?: 0.0
                        ),
                        authorUid = it.getString("author") ?: ""

                    )
                }
            }
            .await()
    }

    override suspend fun getCache(cacheId: String): Outcome<Cache?> = withContext(Dispatchers.IO) {
        return@withContext firestore.collection("caches")
            .document(cacheId)
            .get()
            .awaitOutcome()
            .map { result ->
                Cache(
                    id = result.id,
                    name = result.getString("name") ?: "",
                    description = result.getString("description") ?: "",
                    location = Location(
                        result.getDouble("latitude") ?: 0.0,
                        result.getDouble("longitude") ?: 0.0
                    ),
                    authorUid = result.getString("author") ?: ""
                )
            }
    }

    override suspend fun addCache(cache: Cache): Outcome<Unit> = withContext(Dispatchers.IO) {
        val cacheMap = mapOf(
            "name" to cache.name,
            "description" to cache.description,
            "author" to cache.authorUid,
            "latitude" to cache.location.latitude,
            "longitude" to cache.location.longitude,
        )
        return@withContext firestore.collection("caches")
            .add(cacheMap)
            .awaitUnitOutcome()
            .also { updateCaches() }
    }

    override suspend fun updateCache(cache: Cache): Outcome<Unit> = withContext(Dispatchers.IO) {
        val cacheMap = mapOf(
            "name" to cache.name,
            "description" to cache.description,
            "author" to cache.authorUid,
            "latitude" to cache.location.latitude,
            "longitude" to cache.location.longitude,
        )
        return@withContext firestore.collection("caches").document(cache.id!!)
            .set(cacheMap)
            .awaitOutcome()
            .map { }
            .also { updateCaches() }
    }

    override suspend fun removeCache(cacheId: String): Outcome<Unit> = withContext(Dispatchers.IO) {
        return@withContext firestore.collection("caches").document(cacheId)
            .delete()
            .awaitOutcome()
            .map { }
            .also { updateCaches() }
    }
}