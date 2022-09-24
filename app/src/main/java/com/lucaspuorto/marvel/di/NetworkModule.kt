package com.lucaspuorto.marvel.di

import com.lucaspuorto.marvel.util.Constants.BASE_URL
import com.lucaspuorto.marvel.util.Constants.TIMEOUT_30
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    val network = module {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        }

        val gsonConverter: GsonConverterFactory = GsonConverterFactory.create()

        fun provideHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .readTimeout(TIMEOUT_30, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()

        fun provideRetrofit(httpClient: OkHttpClient, baseUrl: String): Retrofit =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(gsonConverter)
                .build()

        single { provideHttpClient() }
        single { provideRetrofit(get(), BASE_URL) }
    }
}
