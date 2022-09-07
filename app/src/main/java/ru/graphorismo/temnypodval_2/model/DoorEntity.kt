package ru.graphorismo.temnypodval_2.model

class DoorEntity(var difficult: Int, imageId: Int) : AEntity(imageId) {
    override fun getMainInfo(): String {
        return "DIFFICULTY: $difficult"
    }

    override fun getSupportInfo(): String {
        return "DOOR WILL CHANGE A ROOM"
    }

    override fun clone(): AEntity {
        return DoorEntity(difficult, imageId)
    }
}