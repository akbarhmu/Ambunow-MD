package braincore.megalogic.ambunow.di

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
        single { braincore.megalogic.ambunow.data.source.local.datastore.UserDataStoreFactory(
            androidContext()
        ).create() }
    }

    private val networks = module {
        single { FirebaseAuth.getInstance() }
    }

    private val dataSources = module {
        single { braincore.megalogic.ambunow.data.source.local.datasource.UserDataStore(get()) }
    }

    private val repositories = module {
        single {
            braincore.megalogic.ambunow.data.repository.UserRepository(
                get(),
                get(),
                Dispatchers.IO
            )
        }
    }
}