package ru.graphorismo.temnypodval_2.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class EntityData(@PrimaryKey var id : UUID = UUID.randomUUID(),
                      var type: Int = 0,
                      var name: String = "",
                      var health: Int = 0,
                      var restoration: Int = 0,
                      var difficulty: Int = 0,
                      var score: Int = 0,
                      var pictureId: Int = 0,
                      var stackId: Int = 0,
                      var positionId: Int = 0
) {
}