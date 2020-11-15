package com.lucaspuorto.marvel.presentation

sealed class StateResponse<T>

class StateLoading<T> : StateResponse<T>()
class StateSuccess<T>(val data: T) : StateResponse<T>()
class StateError<T>(val error: Throwable) : StateResponse<T>()
