package com.example.downloadfiles_nagwatask.core.di

import com.example.downloadfiles_nagwatask.presentation.activity.MainActivity
import com.example.downloadfiles_nagwatask.presentation.di.FileModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [FileModule::class])
    abstract fun bindMainActivity(): MainActivity
}