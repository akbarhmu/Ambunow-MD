package braincore.megalogic.ambunow.domain

import android.util.Log
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

class LoginUserUseCase(
    private val checkLoginFieldUseCase: CheckLoginFieldUseCase,
    private val saveAuthDataUseCase: SaveAuthDataUseCase,
    private val repository: AuthenticationRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<LoginUserUseCase.Param, UserViewParam?>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<UserViewParam?>> {
        return flow {
            param?.let {
                emit(ViewResource.Loading())
                checkLoginFieldUseCase(param).first().suspendSubscribe(doOnSuccess = { _ ->
                    repository.loginUser(param.email, param.password).collect { authResult ->
                        authResult.suspendSubscribe(doOnSuccess = {
                            val user = authResult.payload?.user
                            user?.let {
                                repository.getUserData(it.uid).collect { userDataResult ->
                                    userDataResult.suspendSubscribe(doOnSuccess = {
                                        val userData = userDataResult.payload
                                        if (userData != null) {
                                            saveAuthDataUseCase(userData).collect { saveAuthResult ->
                                                saveAuthResult.suspendSubscribe(doOnSuccess = {
                                                    emit(
                                                        ViewResource.Success(
                                                            userData.toViewParam()
                                                        )
                                                    )
                                                }, doOnError = { error ->
                                                    emit(ViewResource.Error(error.exception))
                                                })
                                            }
                                        }
                                    }, doOnError = { error ->
                                        emit(ViewResource.Error(error.exception))
                                    })
                                }
                            }
                        }, doOnError = { error ->
                            emit(ViewResource.Error(error.exception))
                        })
                    }
                }, doOnError = { error ->
                    Log.e("checkLoginFieldUseCase", "${error.exception}")
                    emit(ViewResource.Error(error.exception))
                })
            } ?: throw IllegalArgumentException("Param is required.")
        }
    }

    data class Param(val email: String, val password: String)
}