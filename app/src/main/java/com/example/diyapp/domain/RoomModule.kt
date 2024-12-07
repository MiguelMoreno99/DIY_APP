package com.example.diyapp.domain

import android.content.Context
import androidx.room.Room
import com.example.diyapp.data.database.ProjectDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "ProjectDatabase"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ProjectDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideUserDao(db: ProjectDatabase) = db.getUserDao()

    @Singleton
    @Provides
    fun provideCreationDao(db: ProjectDatabase) = db.getCreationsDao()

    @Singleton
    @Provides
    fun provideFavoritesDao(db: ProjectDatabase) = db.getFavoritesDao()
}