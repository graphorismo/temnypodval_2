package ru.graphorismo.temnypodval_2.model.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ EntityData::class ], version=1)
abstract class PodvalDataBase : RoomDatabase() {
     abstract fun roomDAO(): PodvalDAO
}