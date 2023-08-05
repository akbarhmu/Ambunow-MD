package braincore.megalogic.ambunow.base

import braincore.megalogic.ambunow.data.source.DataResource
import braincore.megalogic.ambunow.exception.InvalidCredentialsException
import braincore.megalogic.ambunow.exception.InvalidUserException
import braincore.megalogic.ambunow.exception.NoInternetConnectionException
import braincore.megalogic.ambunow.exception.UnexpectedErrorException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

abstract class BaseRepository {

    suspend fun <T> proceed(coroutine: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            DataResource.Error(exception)
        }
    }

    suspend fun <T> safeFirebaseAuthCall(apiCall: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is FirebaseNetworkException -> {
                    DataResource.Error(NoInternetConnectionException())
                }

                is FirebaseAuthInvalidUserException -> {
                    DataResource.Error(InvalidUserException())
                }

                is FirebaseAuthInvalidCredentialsException -> {
                    DataResource.Error(InvalidCredentialsException())
                }

                else -> {
                    DataResource.Error(UnexpectedErrorException())
                }
            }
        }
    }
}