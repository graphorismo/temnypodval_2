package ru.graphorismo.temnypodval_2.model

abstract class AliveEntity(var health: Int, imageId: Int) : AEntity(imageId) {
    override fun getMainInfo(): String {
        return "HEALTH: $health"
    }
}