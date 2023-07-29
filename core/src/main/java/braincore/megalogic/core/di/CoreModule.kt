package braincore.megalogic.core.di

import braincore.megalogic.core.data.repository.UserRepository
import braincore.megalogic.core.data.source.local.datasource.UserDataStore
import braincore.megalogic.core.data.source.local.datastore.UserDataStoreFactory
import com.google.firebase.auth.FirebaseAuth
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
        single { UserDataStoreFactory(androidContext()).create() }
    }

    private val networks = module {
        single { FirebaseAuth.getInstance() }
    }

    private val dataSources = module {
        single { UserDataStore(get()) }
    }

    private val repositories = module {
        single { UserRepository(get(), get(), Dispatchers.IO) }
    }
}