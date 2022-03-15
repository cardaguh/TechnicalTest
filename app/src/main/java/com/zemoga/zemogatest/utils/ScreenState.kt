package com.zemoga.zemogatest.utils

sealed class ScreenState<out T> {
    object Loading : ScreenState<Nothing>()
    object ErrorServer : ScreenState<Nothing>()
    object InternetError : ScreenState<Nothing>()
    class Render<T>(var renderState: T) : ScreenState<T>()
}