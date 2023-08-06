package braincore.megalogic.ambunow.di

import braincore.megalogic.ambunow.data.repository.AuthenticationRepository
import braincore.megalogic.ambunow.data.repository.UserPreferencesRepository
import braincore.megalogic.ambunow.data.source.local.datasource.UserDataStore
import braincore.megalogic.ambunow.data.source.local.datastore.UserDataStoreFactory
import braincore.megalogic.ambunow.domain.CheckLoginFieldUseCase
import braincore.megalogic.ambunow.domain.LoginUserUseCase
import braincore.megalogic.ambunow.domain.SaveAuthDataUseCase
import braincore.megalogic.ambunow.domain.SyncUserUseCase
import braincore.megalogic.ambunow.ui.features.auth.login.LoginViewModel
import braincore.megalogic.ambunow.ui.features.splashscreen.SplashScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object CoreModule {
    fun getModules() = listOf(
        commons,
        locals,
        networks,
        dataSources,
        repositories,
        useCases,
        viewModels,
    )

    private val commons = module {
        single { Gson() }
    }

    private val locals = module {
        single { UserDataStoreFactory(androidContext()).create() }
    }

    private val networks = module {
        single { FirebaseAuth.getInstance() }
        single { FirebaseFirestore.getInstance() }
    }

    private val dataSources = module {
        single { UserDataStore(get(), get()) }
    }

    private val repositories = module {
        single { AuthenticationRepository(get(), get()) }
        single { UserPreferencesRepository(get()) }
    }

    private val useCases = module {
        single { SaveAuthDataUseCase(get(), Dispatchers.IO) }
        single { LoginUserUseCase(get(), get(), get(), Dispatchers.IO) }
        single { CheckLoginFieldUseCase(Dispatchers.IO) }
        single { SyncUserUseCase(get(), get(), Dispatchers.IO) }
    }

    private val viewModels = module {
        viewModelOf(::LoginViewModel)
        viewModelOf(::SplashScreenViewModel)
    }
}