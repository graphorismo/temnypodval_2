package ru.graphorismo.temnypodval_2.model.data

import android.content.Context
import androidx.room.*

class PodvalRepository private constructor(context: Context) {

    private val database: PodvalDataBase = Room
        .databaseBuilder(
            context.applicationContext,
            PodvalDataBase::class.java,
            "temnypodval2db"
        ) .build()

    suspend fun getAll(): List<EntityData> = database.roomDAO().getAll()
    suspend fun insert(entityData: EntityData) = database.roomDAO().insert(entityData)
    suspend fun update(entityData: EntityData) = database.roomDAO().update(entityData)
    suspend fun delete(entityData: EntityData) = database.roomDAO().delete(entityData)
    suspend fun deleteAll() = database.roomDAO().deleteAll()
    suspend fun insertAll(data: List<EntityData>){
        var dao = database.roomDAO()
        data.forEach(){
            dao.insert(it)
        }
    }

    companion object {
        private var INSTANCE: PodvalRepository? = null
        fun init(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PodvalRepository(context)
            }
        }
        fun get(): PodvalRepository {
            return INSTANCE ?:
            throw IllegalStateException("Repository must be initialized")
        }
    }
}

