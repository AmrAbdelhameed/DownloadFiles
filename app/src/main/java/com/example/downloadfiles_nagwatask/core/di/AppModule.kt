package com.example.downloadfiles_nagwatask.core.di

import android.app.Application
import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.WorkManager
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideWorkManagerInstance(context: Application) =
        WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun providesConstraintToWorkManager(): Constraints {
        return Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    }

    @Provides
    @Singleton
    fun providesDataBuilder() = Data.Builder()

    @Provides
    @Singleton
    fun providesGson() = GsonBuilder().setLenient().create()
}