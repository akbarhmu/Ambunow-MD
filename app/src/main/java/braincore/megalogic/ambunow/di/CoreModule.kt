package braincore.megalogic.ambunow.di

import braincore.megalogic.ambunow.data.repository.UserRepository
import braincore.megalogic.ambunow.data.source.local.datasource.UserDataStore
import braincore.megalogic.ambunow.data.source.local.datastore.UserDataStoreFactory
import braincore.megalogic.ambunow.data.source.remote.datasource.AuthenticationDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object CoreModule {
    fun getModules() = listOf(
        locals,
        networks,
        dataSources,
        repositories,
    )

    private val locals = module {
        single {
            UserDataStoreFactory(
                androidContext()
            ).create()
        }
    }

    private val networks = module {
        single { FirebaseAuth.getInstance() }
        single { FirebaseFirestore.getInstance() }
    }

    private val dataSources = module {
        single { UserDataStore(get()) }
        single { AuthenticationDataSource(get(), get()) }
    }

    private val repositories = module {
        single {
            UserRepository(
                get(), get(), get(), Dispatchers.IO
            )
        }
    }
}