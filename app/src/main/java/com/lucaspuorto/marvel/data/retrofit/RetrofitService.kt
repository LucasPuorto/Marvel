package com.lucaspuorto.marvel.data.retrofit

import com.lucaspuorto.marvel.utils.TIMEOUT_30
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class RetrofitService(url: String) {

    private val gsonConverter: GsonConverterFactory = GsonConverterFactory.create()

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .readTimeout(TIMEOUT_30, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_30, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_30, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(httpClient)
        .addConverterFactory(gsonConverter)
        .build()

    fun <T : Any> create(clazz: KClass<T>): T = retrofit.create(clazz.java)
}
