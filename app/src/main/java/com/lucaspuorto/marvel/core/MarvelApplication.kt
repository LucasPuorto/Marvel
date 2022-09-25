package com.lucaspuorto.marvel.core

import android.app.Application
import com.lucaspuorto.marvel.di.ApiModule
import com.lucaspuorto.marvel.di.DatabaseModule
import com.lucaspuorto.marvel.di.NetworkModule
import com.lucaspuorto.marvel.di.RepositoryModule
import com.lucaspuorto.marvel.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MarvelApplication)
            modules(
                listOf(
                    NetworkModule.network,
                    RepositoryModule.repository,
                    ApiModule.api,
                    ViewModelModule.viewModel,
                    DatabaseModule.database
                )
            )
        }
    }
}