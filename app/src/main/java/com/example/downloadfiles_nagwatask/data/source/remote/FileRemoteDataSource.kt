package com.example.downloadfiles_nagwatask.data.source.remote

import javax.inject.Inject

class FileRemoteDataSource @Inject constructor(private val service: FileAPIService) {
    fun getFiles() = service.getFiles()
}