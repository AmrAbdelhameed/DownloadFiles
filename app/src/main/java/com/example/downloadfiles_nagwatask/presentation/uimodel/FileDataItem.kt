package com.example.downloadfiles_nagwatask.presentation.uimodel

data class FileDataItem(
    val id: Int? = null,
    val type: String? = null,
    val url: String? = null,
    var name: String? = null,
    var fileUri: String = "",
    var isDownloaded: Boolean = false,
    var percentage: Int = 0
)