package ru.graphorismo.temnypodval_2.model

class PlayerEntity(var score: Int, health: Int, imageId: Int) : AliveEntity(health, imageId) {
    override fun getSupportInfo(): String {
        return "SCORE: $score"
    }
}