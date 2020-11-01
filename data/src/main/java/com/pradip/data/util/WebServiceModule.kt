package com.pradip.data.util

import com.pradip.data.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val webServiceModule = module {

    single { get<Retrofit>().create<ApiService>()}

}
