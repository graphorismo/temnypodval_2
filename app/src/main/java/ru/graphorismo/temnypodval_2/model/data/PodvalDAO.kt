package ru.graphorismo.temnypodval_2.model.data

import androidx.room.*
import java.util.*

@Dao
interface PodvalDAO {
    @Query("SELECT * FROM entitydata")
    suspend fun getAll(): List<EntityData>

    @Query("DELETE FROM entitydata")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(entityData: EntityData);

    @Update
    suspend fun update(entityData: EntityData);

    @Delete
    suspend fun delete(entityData: EntityData);
}