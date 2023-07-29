package braincore.megalogic.ambunow

import android.app.Application
import braincore.megalogic.ambunow.di.AppModule
import braincore.megalogic.core.di.CoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AmbunowApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AmbunowApplication)
            modules(
                AppModule.getModules() +
                CoreModule.getModules()
            )
        }
    }

}