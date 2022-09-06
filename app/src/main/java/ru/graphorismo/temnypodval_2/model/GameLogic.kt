package ru.graphorismo.temnypodval_2.model

import ru.graphorismo.temnypodval_2.R

class GameLogic()
{
    private lateinit var entitiesInRoom: MutableList<MutableList<AEntity>>
    private var stackIDInRoom = 0
    private var entityIDInStack = 0
    private var player: PlayerEntity = PlayerEntity(0,100, 0)

    init {
        generateNewRoomWithDifficulty(1)
    }

    fun onInteraction()
    {
        var gameEntity = entitiesInRoom[stackIDInRoom][entityIDInStack]
        if(gameEntity is ChestEntity)
            useChest(gameEntity)
        else if (gameEntity is DoorEntity)
            generateNewRoomWithDifficulty(gameEntity.difficult)
        else if (gameEntity is MonsterEntity){
            makePlayerAndMonsterHitEachOther(gameEntity)
        }

    }

    private fun useChest(chest: ChestEntity) {
        player.health+=chest.healthRestore
        entitiesInRoom[stackIDInRoom].remove(chest)
        onSwitchNext()
    }

    private fun makePlayerAndMonsterHitEachOther(monster: MonsterEntity) {
            player.health -= monster.health/10
            monster.health -= player.health/10
            if (monster.health <= 0) handleMonsterDeath(monster)
            if (player.health <= 0) handlePlayerDeath()
    }

    private fun handlePlayerDeath() {
        player = PlayerEntity(0,100,0)
        generateNewRoomWithDifficulty(1)
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
        entitiesInRoom = mutableListOf<MutableList<AEntity>>()
        entitiesInRoom.add(mutableListOf<AEntity>())
        entitiesInRoom[0].add(DoorEntity(difficult+1, R.drawable.door))
        for (i in 0 until difficult+1){
            entitiesInRoom[0].add(MonsterEntity("Monster $i", 50*difficult, R.drawable.skeleton))
        }
        for (i in 1 until difficult+3){
            entitiesInRoom.add(mutableListOf<AEntity>())
            entitiesInRoom[i].add(ChestEntity(difficult*100,R.drawable.chest))
            for (j in 0 until difficult+1){
                entitiesInRoom[i].add(MonsterEntity("Monster $i$j", 50*difficult, R.drawable.skeleton))
            }
        }
        stackIDInRoom=0
        entityIDInStack = entitiesInRoom[stackIDInRoom].size-1
    }

    fun getCurrentEntity(): AEntity {
        return entitiesInRoom[stackIDInRoom][entityIDInStack]
    }

    fun onSwitchNext() {
        stackIDInRoom+=1
        var roomIsOver: Boolean = stackIDInRoom>=entitiesInRoom.size
        if (roomIsOver) stackIDInRoom = 0
        entityIDInStack = entitiesInRoom[stackIDInRoom].size-1

    }

    fun onSwitchPrev() {
        stackIDInRoom-=1
        var roomIsOver: Boolean = stackIDInRoom<0
        if (roomIsOver) stackIDInRoom = entitiesInRoom.size-1
        entityIDInStack = entitiesInRoom[stackIDInRoom].size-1
    }

    fun getPlayer(): PlayerEntity {
        return player
    }

}