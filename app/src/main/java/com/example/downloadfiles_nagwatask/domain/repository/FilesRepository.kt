package com.example.downloadfiles_nagwatask.domain.repository

import com.example.downloadfiles_nagwatask.domain.entity.FileEntity
import io.reactivex.Observable

interface FilesRepository {
    fun getFiles(): Observable<List<FileEntity>>
}