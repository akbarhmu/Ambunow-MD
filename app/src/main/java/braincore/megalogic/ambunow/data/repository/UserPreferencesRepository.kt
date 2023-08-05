package braincore.megalogic.ambunow.data.repository

import braincore.megalogic.ambunow.base.BaseRepository
import braincore.megalogic.ambunow.data.source.DataResource
import braincore.megalogic.ambunow.data.source.local.datasource.UserDataStore
import braincore.megalogic.ambunow.data.source.remote.model.RemoteUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserPreferencesRepository(
    private val dataSource: UserDataStore
) : BaseRepository() {

    suspend fun setCurrentUser(user: RemoteUser): Flow<DataResource<Unit>> = flow {
        emit(proceed { dataSource.setCurrentUser(user) })
    }
}