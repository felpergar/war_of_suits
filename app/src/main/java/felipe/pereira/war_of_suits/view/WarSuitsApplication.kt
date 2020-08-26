package felipe.pereira.war_of_suits.view

import android.app.Application
import felipe.pereira.war_of_suits.di.appModule
import org.koin.core.context.startKoin

class WarSuitsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}