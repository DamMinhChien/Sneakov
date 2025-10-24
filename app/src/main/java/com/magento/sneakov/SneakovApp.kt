package com.magento.sneakov

import android.app.Application
import com.magento.sneakov.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SneakovApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SneakovApp)
            modules(appModule)
        }
    }
}