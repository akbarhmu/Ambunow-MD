package braincore.megalogic.core.data.repository

import android.util.Log
import braincore.megalogic.core.data.source.Resource
import braincore.megalogic.core.data.source.local.datasource.UserDataStore
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(
    private val firebaseAuth: FirebaseAuth,
    private val dataStore: UserDataStore,
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

    suspend fun logout() {
        try {
            firebaseAuth.signOut()
            dataStore.clear()
        } catch(e: Exception) {
            Log.e("UserRepository", "logout: ${e.localizedMessage}")
        }
    }
}