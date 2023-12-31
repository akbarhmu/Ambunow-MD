package braincore.megalogic.ambunow.domain

import braincore.megalogic.ambunow.base.BaseUseCase
import braincore.megalogic.ambunow.data.repository.AuthenticationRepository
import braincore.megalogic.ambunow.data.source.remote.model.toViewParam
import braincore.megalogic.ambunow.ui.viewparam.UserViewParam
import braincore.megalogic.ambunow.ui.wrapper.ViewResource
import braincore.megalogic.ambunow.utils.ext.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

typealias SyncResult = Pair<Boolean, UserViewParam?>

class SyncUserUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val saveAuthDataUseCase: SaveAuthDataUseCase,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Nothing, SyncResult>(dispatcher) {
    override suspend fun execute(param: Nothing?): Flow<ViewResource<SyncResult>> {
        return flow {
            authenticationRepository.isUserLoggedIn().first()
                .suspendSubscribe(doOnSuccess = { result ->
                    if (result.payload?.first == true && result.payload.second.isNotEmpty()) {
                        val userId = result.payload.second
                        Timber.tag("SyncUserUseCase").d("User $userId logged in")
                        authenticationRepository.getUserData(userId).collect { userDataResult ->
                            userDataResult.suspendSubscribe(doOnSuccess = {
                                val userData = userDataResult.payload
                                if (userData != null) {
                                    Timber.tag("SyncUserUseCase").d("User data: $userData")
                                    saveAuthDataUseCase(userData).collect { saveAuthResult ->
                                        saveAuthResult.suspendSubscribe(doOnSuccess = {
                                            Timber.tag("SyncUserUseCase").d("User data saved")
                                            emit(
                                                ViewResource.Success(
                                                    Pair(true, userData.toViewParam())
                                                )
                                            )
                                        }, doOnError = { error ->
                                            Timber.tag("SyncUserUseCase")
                                                .e("Failed to save user data")
                                            Timber.tag("SyncUserUseCase").e(error.exception)
                                            emit(ViewResource.Error(error.exception))
                                        })
                                    }
                                }
                            }, doOnError = { error ->
                                Timber.tag("SyncUserUseCase").e("Failed to get user data")
                                Timber.tag("SyncUserUseCase").e(error.exception)
                                emit(ViewResource.Error(error.exception))
                            })
                        }
                    } else {
                        emit(
                            ViewResource.Success(Pair(false, null))
                        )
                    }
                }, doOnError = { error ->
                    Timber.tag("SyncUserUseCase").e("Failed to check user login status")
                    Timber.tag("SyncUserUseCase").e(error.exception)
                    emit(ViewResource.Error(error.exception))
                })
        }
    }
}