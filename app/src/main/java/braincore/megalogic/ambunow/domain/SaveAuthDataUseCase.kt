package braincore.megalogic.ambunow.domain

import braincore.megalogic.ambunow.base.BaseUseCase
import braincore.megalogic.ambunow.data.repository.UserPreferencesRepository
import braincore.megalogic.ambunow.data.source.DataResource
import braincore.megalogic.ambunow.data.source.remote.model.RemoteUser
import braincore.megalogic.ambunow.ui.wrapper.ViewResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class SaveAuthDataUseCase(
    private val repository: UserPreferencesRepository, dispatcher: CoroutineDispatcher
) : BaseUseCase<RemoteUser, Boolean>(dispatcher) {
    override suspend fun execute(param: RemoteUser?): Flow<ViewResource<Boolean>> = flow {
        param?.let {
            val saveUser = repository.setCurrentUser(it).first()
            val saveUserId = repository.setUserId(it.userId).first()

            if (saveUser is DataResource.Success && saveUserId is DataResource.Success) {
                Timber.tag("SaveAuthDataUseCase").d("Success save local data")
                emit(ViewResource.Success(true))
            } else {
                Timber.tag("SaveAuthDataUseCase").e("Failed to save local data.")
                emit(ViewResource.Error(IllegalStateException("Failed to save local data.")))
            }
        }
    }
}