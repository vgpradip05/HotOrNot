package com.pradip.data.util

import com.pradip.data.AppDb
import org.koin.dsl.module

fun daoModule(appDb: AppDb) = module {

    single { appDb.profileDao() }


}

