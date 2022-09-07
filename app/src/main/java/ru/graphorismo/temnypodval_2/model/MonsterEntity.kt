package ru.graphorismo.temnypodval_2.model

class MonsterEntity(var name: String, health: Int, imageId: Int): AliveEntity(health, imageId) {
    override fun getSupportInfo(): String {
        return "THATS ${name}! THIS MONSTER WILL HIT YOU"
    }

    override fun clone(): AEntity {
        return MonsterEntity(name, health, imageId)
    }
}