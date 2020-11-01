package com.pradip.core.dependencies

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pradip.core.BuildConfig
import com.pradip.core.networking.BaseResponseConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "baseUrl"
private const val USE_INTERCEPTOR = "useInterceptor"
private const val API_KEY = "api_key"

val networkModule = module {

    /** [kotlinx.coroutines.Deferred] support for Retrofit **/
    single<CallAdapter.Factory> { CoroutineCallAdapterFactory() }

    /** Stetho Interceptor for Network Traffic **/
    single<Interceptor> { StethoInterceptor() }

    /** Base URL for Retrofit **/
    single(qualifier = StringQualifier(BASE_URL)) { BuildConfig.BASE_URL }

    /** Use the Stetho interceptor **/
    single(qualifier = StringQualifier(USE_INTERCEPTOR)) { BuildConfig.DEBUG }

    /** OkHttp client for Retrofit **/
    single {
        OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val originalRequest = chain.request()

                val urlWithQueryParams = originalRequest.url.newBuilder()
                    .build()

                chain.proceed(
                    originalRequest.newBuilder().url(urlWithQueryParams).build()
                )
            }

            if (get(qualifier = StringQualifier(USE_INTERCEPTOR)))
                addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )

        }.build()
    }

    /** GSON **/
    single {
        GsonBuilder()
            .create()
    }

    /** GSON parsing for Retrofit **/
    single<GsonConverterFactory> { GsonConverterFactory.create(get()) }
    single<Converter.Factory>(named("BaseResponseConverterFactory")) {
        BaseResponseConverterFactory(get())
    }

    /** Retrofit **/
    single {
        Retrofit.Builder().apply {
            baseUrl(get<String>(qualifier = StringQualifier(BASE_URL)))
            client(get())
            addCallAdapterFactory(get())
            addConverterFactory(get(named("BaseResponseConverterFactory")))

        }.build()
    }
}
