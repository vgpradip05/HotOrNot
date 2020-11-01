package com.pradip.core.dependencies

import com.pradip.core.dispatchers.AppDispatcher
import com.pradip.core.dispatchers.Dispatcher
import org.koin.dsl.module

val schedulerModule = module {

    /** The application-level dispatcher for scheduling work on threads **/
    single<Dispatcher> { AppDispatcher() }

}
