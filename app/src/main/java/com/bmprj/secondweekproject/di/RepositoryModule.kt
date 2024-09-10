package com.bmprj.secondweekproject.di

import com.bmprj.secondweekproject.repository.DbRepository
import com.bmprj.secondweekproject.repository.DbRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindDbRepository(dbRepositoryImpl: DbRepositoryImpl):DbRepository
}