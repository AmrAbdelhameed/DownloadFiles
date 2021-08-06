package com.example.downloadfiles_nagwatask.domain.interactor

import com.example.downloadfiles_nagwatask.domain.entity.FileEntity
import com.example.downloadfiles_nagwatask.domain.repository.FilesRepository
import io.reactivex.Observable
import javax.inject.Inject

class FilesUseCase @Inject constructor(private val repository: FilesRepository) {
    fun getFiles(): Observable<List<FileEntity>> {
        return repository.getFiles()
    }
}