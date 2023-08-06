package braincore.megalogic.ambunow.data.repository

import braincore.megalogic.ambunow.base.BaseRepository
import braincore.megalogic.ambunow.data.source.DataResource
import braincore.megalogic.ambunow.data.source.remote.model.RemoteUser
import braincore.megalogic.ambunow.utils.COLLECTION_USERS
import braincore.megalogic.ambunow.utils.await
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthenticationRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : BaseRepository() {
    suspend fun loginUser(email: String, password: String): Flow<DataResource<AuthResult>> {
        return flow {
            emit(safeFirebaseAuthCall { firebaseAuth.signInWithEmailAndPassword(email, password).await() })
        }
    }

    suspend fun getUserData(userId: String): Flow<DataResource<RemoteUser>> {
        return flow {
            emit(safeFirebaseAuthCall { firebaseFirestore.collection(COLLECTION_USERS).document(userId).get().await().toObject(RemoteUser::class.java)!! })
        }
    }

    suspend fun isUserLoggedIn(): Flow<DataResource<Boolean>> = flow {
        emit(safeFirebaseAuthCall { firebaseAuth.currentUser != null })
    }
}