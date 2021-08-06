package com.example.downloadfiles_nagwatask.data.repository

import com.example.downloadfiles_nagwatask.data.source.remote.FileRemoteDataSource
import com.example.downloadfiles_nagwatask.domain.entity.FileEntity
import com.example.downloadfiles_nagwatask.domain.repository.FilesRepository
import io.reactivex.Observable

class FilesRepositoryImpl constructor(
    private val fileRemoteDataSource: FileRemoteDataSource
) : FilesRepository {

    override fun getFiles(): Observable<List<FileEntity>> {
        return fileRemoteDataSource.getFiles()
    }
}