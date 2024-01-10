package com.thebrownfoxx.marballs.services.caches
import com.google.firebase.firestore.FirebaseFirestore
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.addOnOutcomeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class FirestoreCacheRepository(private val firestore: FirebaseFirestore) : CacheRepository {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _caches = MutableStateFlow<List<Cache>?>(null)
    override val caches = _caches.asStateFlow()

    init {
        updateCaches()
    }

    fun updateCaches() {
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
                            it.getDouble("longitude") ?: 0.0) ,
                        authorUid = it.getString("authorUid") ?: ""

                    )
                }
            }
    }

    override fun addCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        val cacheMap = mapOf(
            "name" to cache.name,
            "description" to cache.description,
            "author" to cache.authorUid,
            "latitude" to cache.location.latitude,
            "longitude" to cache.location.longitude

        )
        firestore.collection("caches")
            .add(cacheMap)
            .addOnOutcomeListener{
                if (it is Outcome.Success){
                    updateCaches()
                }
                onOutcomeReceived(it)
            }
    }

    override fun updateCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {

        firestore.collection("caches").document(cache.id!!)
            .set(cache)
            .addOnOutcomeListener{
                if (it is Outcome.Success){
                    updateCaches()
                }
                onOutcomeReceived(it)
            }
    }

    override fun removeCache(cache: Cache, onOutcomeReceived: (Outcome<Unit>) -> Unit) {
        firestore.collection("caches").document(cache.id!!)
            .delete()
            .addOnOutcomeListener{
                if (it is Outcome.Success){
                    updateCaches()
                }
                onOutcomeReceived(it)
            }
    }
}