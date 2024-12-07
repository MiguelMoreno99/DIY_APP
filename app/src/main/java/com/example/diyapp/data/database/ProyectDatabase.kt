package com.example.diyapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diyapp.data.database.daos.CreationsDao
import com.example.diyapp.data.database.daos.FavoriteDao
import com.example.diyapp.data.database.daos.UserDao
import com.example.diyapp.data.database.entities.CreationEntity
import com.example.diyapp.data.database.entities.FavoriteEntity
import com.example.diyapp.data.database.entities.UserEntity

@Database(
    entities = [UserEntity::class, CreationEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class ProjectDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getCreationsDao(): CreationsDao
    abstract fun getFavoritesDao(): FavoriteDao
}