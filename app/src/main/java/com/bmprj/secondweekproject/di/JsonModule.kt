package com.bmprj.secondweekproject.di

import com.bmprj.secondweekproject.repository.JsonRepository
import com.bmprj.secondweekproject.repository.JsonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
interface JsonModule {

    @Binds
    @ViewModelScoped
    fun bindJsonRepo(jsonRepositoryImpl: JsonRepositoryImpl):JsonRepository
}