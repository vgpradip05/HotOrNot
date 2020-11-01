package com.pradip.core.dependencies

import android.preference.PreferenceManager
import org.koin.dsl.module

val storageModule = module {

    single { PreferenceManager.getDefaultSharedPreferences(get()) }

}
