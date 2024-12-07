package com.example.diyapp.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.diyapp.data.database.entities.CreationEntity

@Dao
interface CreationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPublication(publications: List<CreationEntity>)

    @Query("SELECT * FROM CreationTable WHERE state = 1")
    suspend fun getAllCompletedPublications(): List<CreationEntity>

    @Query("SELECT * FROM CreationTable WHERE state = 1 ORDER BY theme ASC")
    suspend fun getAllCompletedPublicationsAsc(): List<CreationEntity>

    @Query("SELECT * FROM CreationTable WHERE state = 1 ORDER BY theme DESC")
    suspend fun getAllCompletedPublicationsDesc(): List<CreationEntity>

    @Query("SELECT * FROM CreationTable WHERE state = 1 AND email= :email")
    suspend fun getAllCompletedPublicationsByUser(email: String): List<CreationEntity>

    @Query("SELECT * FROM CreationTable WHERE idPublication = :id")
    suspend fun getPublicationById(id: Int): CreationEntity

    @Query("SELECT * FROM CreationTable WHERE state = 0 AND email= :email")
    suspend fun getDraftPublicationsByUser(email: String): List<CreationEntity>

    @Update
    suspend fun updatePublication(publication: CreationEntity)

    @Query(value = "DELETE FROM CreationTable")
    suspend fun deleteAllPublications()
}