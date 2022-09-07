package ru.graphorismo.temnypodval_2.model

class PlayerEntity(var score: Int, health: Int, imageId: Int) : AliveEntity(health, imageId) {
    override fun getSupportInfo(): String {
        return "PLAYER SCORE: $score"
    }

    override fun getMainInfo(): String {
        return "PLAYER HEALTH $health"
    }

    override fun clone(): AEntity {
        return PlayerEntity(score, health, imageId)
    }
}