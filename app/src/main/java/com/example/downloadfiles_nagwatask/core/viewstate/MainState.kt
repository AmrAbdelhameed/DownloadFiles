package com.example.downloadfiles_nagwatask.core.viewstate

sealed class MainState {
    object LOADING : MainState()
    object SUCCESS : MainState()
    object ERROR : MainState()
}