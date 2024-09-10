package com.bmprj.secondweekproject.di

import android.content.Context
import androidx.room.Room
import com.bmprj.secondweekproject.data.WordDAO
import com.bmprj.secondweekproject.data.WordDatabase
import com.bmprj.secondweekproject.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DbModule {

    @Provides
    @ViewModelScoped
    fun provideDatabase(@ApplicationContext context:Context):WordDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            WordDatabase::class.java,
            Constants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideDAO(db:WordDatabase):WordDAO{
        return db.wordDAO()
    }
}