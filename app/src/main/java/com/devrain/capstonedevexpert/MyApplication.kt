package com.devrain.capstonedevexpert

import android.app.Application
import com.devrain.capstonedevexpert.core.di.databaseModule
import com.devrain.capstonedevexpert.core.di.networkModule
import com.devrain.capstonedevexpert.core.di.repositoryModule
import com.devrain.capstonedevexpert.di.useCaseModule
import com.devrain.capstonedevexpert.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}