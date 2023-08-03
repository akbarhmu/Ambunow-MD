package braincore.megalogic.ambunow.data.repository

import android.util.Log
import braincore.megalogic.ambunow.data.source.Resource
import braincore.megalogic.ambunow.data.source.local.datasource.UserDataStore
import braincore.megalogic.ambunow.data.source.remote.datasource.AuthenticationDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class UserRepository(
    private val firebaseAuth: FirebaseAuth,
    private val dataStore: UserDataStore,
    private val authenticationDataSource: AuthenticationDataSource,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun isUserLoggedIn(): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>> {
            if (firebaseAuth.currentUser != null) {
                emit(Resource.Success(true))
            } else {
                emit(Resource.Success(false))
            }
        }.catch { e ->
            emit(Resource.Error(-1, e.localizedMessage ?: "Something wrong."))
        }.flowOn(dispatcher)
    }

    suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser>> = flow<Resource<FirebaseUser>> {
        val result = authenticationDataSource.login(email, password)
        val user = result.user
        if (user != null) {
            Log.d("UserRepository", "User ${user.uid} logged in")
            emit(Resource.Success(user))
        }
    }.catch {
        Log.e("UserRepository", "Error logging in: ${it.message}")
        emit(Resource.Error(-1, it.localizedMessage.orEmpty()))
    }.onStart {
        emit(Resource.Loading)
    }.flowOn(dispatcher)

    suspend fun logout() {
        try {
            firebaseAuth.signOut()
            dataStore.clear()
        } catch(e: Exception) {
            Log.e("UserRepository", "logout: ${e.localizedMessage}")
        }
    }
}