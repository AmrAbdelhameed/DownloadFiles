package com.example.downloadfiles_nagwatask.data.source.remote

import com.example.downloadfiles_nagwatask.domain.entity.FileEntity
import io.reactivex.Observable
import retrofit2.http.GET

interface FileAPIService {
    @GET("movies")
    fun getFiles(): Observable<List<FileEntity>>
}