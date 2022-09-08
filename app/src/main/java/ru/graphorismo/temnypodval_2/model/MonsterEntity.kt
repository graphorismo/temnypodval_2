package ru.graphorismo.temnypodval_2.model

class MonsterEntity(var name: String, health: Int, imageId: Int): AliveEntity(health, imageId) {
    override fun getSupportInfo(): String {
        return "${name} WILL HIT YOU!"
    }

    override fun getMainInfo(): String {
        return "MONSTER HP:$health"
    }

    override fun clone(): AEntity {
        return MonsterEntity(name, health, imageId)
    }
}