package braincore.megalogic.ambunow

import android.app.Application
import braincore.megalogic.ambunow.di.CoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.Forest.plant

class AmbunowApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@AmbunowApplication)
            modules(
                CoreModule.getModules()
            )
        }
    }

}