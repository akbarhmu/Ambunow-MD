package braincore.megalogic.ambunow.di

import braincore.megalogic.ambunow.ui.features.splashscreen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModule {
    fun getModules() = listOf(
        viewModels
    )

    private val viewModels = module {
        viewModelOf(::SplashScreenViewModel)
    }
}