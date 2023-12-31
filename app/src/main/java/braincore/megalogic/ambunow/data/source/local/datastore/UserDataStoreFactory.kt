package braincore.megalogic.ambunow.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class UserDataStoreFactory(
    private val context: Context
) {
    fun create(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCE_NAME) }
        )
    }

    companion object {
        private const val USER_PREFERENCE_NAME = "user_preference"
    }
}

object UserPreferenceKey {
    val userObject = stringPreferencesKey(PreferenceKey.PREF_USER_OBJECT)
    val userId = stringPreferencesKey(PreferenceKey.PREF_USER_ID)
}

object PreferenceKey {
    const val PREF_USER_OBJECT = "PREF_USER_OBJECT"
    const val PREF_USER_ID = "PREF_USER_ID"
}