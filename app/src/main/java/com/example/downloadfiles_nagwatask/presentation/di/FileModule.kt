package com.example.downloadfiles_nagwatask.presentation.di

import com.example.downloadfiles_nagwatask.core.utils.RetrofitClient
import com.example.downloadfiles_nagwatask.data.repository.FilesRepositoryImpl
import com.example.downloadfiles_nagwatask.data.source.remote.FileAPIService
import com.example.downloadfiles_nagwatask.data.source.remote.FileRemoteDataSource
import com.example.downloadfiles_nagwatask.domain.repository.FilesRepository
import dagger.Module
import dagger.Provides

@Module
class FileModule {
    @Provides
    fun provideFileServiceAPIs(): FileAPIService = RetrofitClient().createService(FileAPIService::class.java)

    @Provides
    fun provideFilesRepository(remoteDataSource: FileRemoteDataSource): FilesRepository {
        return FilesRepositoryImpl(remoteDataSource)
    }
}