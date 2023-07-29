package braincore.megalogic.core.data.source.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

class UserDataStore(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun <T> storeData(key: Preferences.Key<T>, value: T) {
        dataStore.edit { pref ->
            pref[key] = value
        }
    }

    suspend fun clear() {
        dataStore.edit { pref ->
            pref.clear()
        }
    }
}