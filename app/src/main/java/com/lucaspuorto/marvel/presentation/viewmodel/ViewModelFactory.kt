package com.lucaspuorto.marvel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lucaspuorto.marvel.data.repository.MarvelRepository

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = HomeViewModel(MarvelRepository()) as T
}