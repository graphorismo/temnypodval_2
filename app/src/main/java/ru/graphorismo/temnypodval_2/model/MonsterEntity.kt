package ru.graphorismo.temnypodval_2.model

class MonsterEntity(var name: String, health: Int, imageId: Int): AliveEntity(health, imageId) {
    override fun getSupportInfo(): String {
        return "MONSTER ${name} WILL HIT YOU!"
    }

    override fun getMainInfo(): String {
        return "MONSTER HEALTH $health"
    }

    override fun clone(): AEntity {
        return MonsterEntity(name, health, imageId)
    }
}