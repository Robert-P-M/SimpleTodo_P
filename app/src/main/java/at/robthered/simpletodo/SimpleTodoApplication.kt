package at.robthered.simpletodo

import android.app.Application
import at.robthered.simpletodo.features.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinContext

class SimpleTodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SimpleTodoApplication)
        }
    }
}