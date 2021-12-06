package com.example.testapp1

import android.app.Application
import com.example.testapp1.di.business.businessModule
import com.example.testapp1.di.data.localModule
import com.example.testapp1.di.data.remoteModule
import com.example.testapp1.di.data.repositoryModule
import com.example.testapp1.di.feature.featureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@NewsApplication)
            modules(
                listOf(
                    localModule,
                    remoteModule,
                    repositoryModule,
                    businessModule,
                    featureModule
                )
            )
        }
    }
}