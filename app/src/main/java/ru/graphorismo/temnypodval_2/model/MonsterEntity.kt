package ru.graphorismo.temnypodval_2.model

class MonsterEntity(var name: String, health: Int, imageId: Int): AliveEntity(health, imageId) {
    override fun getSupportInfo(): String {
        return "MONSTER WILL HIT YOU"
    }
}