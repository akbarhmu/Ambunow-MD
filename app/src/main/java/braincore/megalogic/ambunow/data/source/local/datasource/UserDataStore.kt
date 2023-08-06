package braincore.megalogic.ambunow.data.source.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import braincore.megalogic.ambunow.data.source.local.datastore.UserPreferenceKey
import braincore.megalogic.ambunow.data.source.remote.model.RemoteUser
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore(
    private val dataStore: DataStore<Preferences>, private val gson: Gson
) {
    suspend fun <T> storeData(key: Preferences.Key<T>, value: T) {
        dataStore.edit { pref ->
            pref[key] = value
        }
    }

    suspend fun setCurrentUser(user: RemoteUser) {
        dataStore.edit {
            it[UserPreferenceKey.userObject] = gson.toJson(user)
        }
    }

    fun getCurrentUser(): Flow<RemoteUser> {
        return dataStore.data.map {
            gson.fromJson(
                it.toPreferences()[UserPreferenceKey.userObject].orEmpty(),
                RemoteUser::class.java
            )
        }
    }

    suspend fun getUserId(): Flow<String> {
        return dataStore.data.map {
            it.toPreferences()[UserPreferenceKey.userId].orEmpty()
        }
    }

    suspend fun setUserId(newUserId: String) {
        dataStore.edit {
            it[UserPreferenceKey.userId] = newUserId
        }
    }

    suspend fun clearData() {
        dataStore.edit { pref ->
            pref.clear()
        }
    }
}