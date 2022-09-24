package com.lucaspuorto.marvel.di

import com.lucaspuorto.marvel.viewmodel.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {

    val viewModel = module {

        viewModel { CharactersViewModel(get()) }
    }
}