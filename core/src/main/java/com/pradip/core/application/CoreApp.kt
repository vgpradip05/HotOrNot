package com.pradip.core.application

import android.app.Application
import com.pradip.core.dependencies.androidModule
import com.pradip.core.dependencies.networkModule
import com.pradip.core.dependencies.schedulerModule
import com.pradip.core.dependencies.storageModule
import com.facebook.stetho.Stetho
import com.pradip.core.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module


abstract class CoreApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initialiseStetho()
        initialiseKoin()
    }

    private fun initialiseStetho() {
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }

    private fun initialiseKoin() {
        startKoin {

            // Logger for Android
            androidLogger()

            // Set the Android Context
            androidContext(this@CoreApp)

            modules(listOf(*defaultModules(), *getDataModules()))
        }
    }

    private fun defaultModules(): Array<Module> =
        arrayOf(androidModule, networkModule, storageModule, schedulerModule)

    /**
     * Return the modules for Data (Dao, WebServices)
     */
    abstract fun getDataModules(): Array<Module>

}
