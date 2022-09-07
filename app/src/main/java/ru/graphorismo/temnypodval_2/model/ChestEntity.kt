package ru.graphorismo.temnypodval_2.model

class ChestEntity(var healthRestore: Int, imageId: Int) : AEntity(imageId) {
    override fun getMainInfo(): String {
        return "RESTORATION: $healthRestore"
    }

    override fun getSupportInfo(): String {
        return "CHEST WILL RESTORE YOUR HEALTH"
    }

    override fun clone(): AEntity {
        return ChestEntity(healthRestore, imageId)
    }
}