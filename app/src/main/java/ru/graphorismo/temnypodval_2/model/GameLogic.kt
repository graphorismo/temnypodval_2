package ru.graphorismo.temnypodval_2.model

class GameLogic()
{
    var entitiesInRoom = arrayOf<MutableList<AEntity>>()
    var stackIDInRoom = 0
    var entityIDInStack = 0
    var player: PlayerEntity = PlayerEntity(0,0,0)

    fun onInteraction()
    {
        var gameEntity = entitiesInRoom[stackIDInRoom][entityIDInStack]
        if(gameEntity is ChestEntity)
            addPlayerHealth(gameEntity.healthRestore)
        else if (gameEntity is DoorEntity)
            generateNewRoomWithDifficulty(gameEntity.difficult)
        else if (gameEntity is MonsterEntity){
            makePlayerAndMonsterHitEachOther(gameEntity)
        }

    }

    private fun makePlayerAndMonsterHitEachOther(monster: MonsterEntity) {
            player.health -= monster.health/10
            monster.health -= player.health/10
            if (monster.health < 0) handleMonsterDeath(monster)
            if (player.health <0) handlePlayerDeath()
    }

    private fun handlePlayerDeath() {
        player = PlayerEntity(0,100,0)
        generateNewRoomWithDifficulty(0)
    }

    private fun handleMonsterDeath(monster: AEntity) {
        player.score += 1
        entitiesInRoom.forEach {
            if (it.contains(monster))
                it.remove(monster)
        }
        entityIDInStack = entitiesInRoom[stackIDInRoom].size-1
    }

    private fun generateNewRoomWithDifficulty(difficult: Int) {
        entitiesInRoom = arrayOf<MutableList<AEntity>>()
        entitiesInRoom.plus(mutableListOf<AEntity>())
        entitiesInRoom[0].plus(DoorEntity(difficult+1, 0))
        for (i in 0 until difficult+1){
            entitiesInRoom[0].plus(MonsterEntity("Monster", 50*difficult, 0))
        }
        for (i in 1 until difficult+2){
            entitiesInRoom.plus(mutableListOf<AEntity>())
            entitiesInRoom[i].plus(ChestEntity(difficult*100,0))
            for (j in 0 until difficult+1){
                entitiesInRoom[i].plus(MonsterEntity("Monster", 50*difficult, 0))
            }
        }
    }

    private fun addPlayerHealth(healthRestore: Int) {
        player.health += healthRestore
    }

    fun getCurrentEntity(): AEntity {
        return entitiesInRoom[stackIDInRoom][entityIDInStack]
    }

    fun onSwitchNext() {
        var roomIsOver: Boolean = stackIDInRoom>=entitiesInRoom.size
        if (roomIsOver) stackIDInRoom = 0
        entityIDInStack = entitiesInRoom[stackIDInRoom].size-1

    }

    fun onSwitchPrev() {
        var roomIsOver: Boolean = stackIDInRoom<0
        if (roomIsOver) stackIDInRoom = entitiesInRoom.size-1
        entityIDInStack = entitiesInRoom[stackIDInRoom].size-1
    }

}