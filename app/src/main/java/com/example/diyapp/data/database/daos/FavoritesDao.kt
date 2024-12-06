package com.example.diyapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.diyapp.data.database.entities.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("""
        SELECT 
            Pub.idPublication, 
            Pub.email, 
            Pub.theme, 
            Pub.photoMain, 
            Pub.description,
            Pub.numLikes, 
            Pub.state, 
            Pub.dateCreation, 
            Pub.instructions, 
            Pub.photoProcess
        FROM 
            CreationTable AS Pub
        JOIN 
            userTable ON userTable.email = Pub.email
        JOIN 
            favoritesTable ON favoritesTable.idPublication = Pub.idPublication
        WHERE 
            favoritesTable.email = :email
    """)
    suspend fun getFavoritesByUser(email: String): List<FavoriteEntity>
}