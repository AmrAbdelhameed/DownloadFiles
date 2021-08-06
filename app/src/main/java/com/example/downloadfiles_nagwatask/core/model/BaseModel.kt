package com.example.downloadfiles_nagwatask.core.model

import com.example.downloadfiles_nagwatask.core.viewstate.MainState

data class BaseModel<out T>(
    val state: MainState,
    val data: T? = null,
    val message: String? = null
)