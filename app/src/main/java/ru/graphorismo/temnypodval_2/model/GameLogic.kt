package ru.graphorismo.temnypodval_2.model

class GameLogic(var callbacks: Callbacks)
{
    interface Callbacks
    {
        fun onChestEntity(entity: ChestEntity)
        fun onDoorEntity(entity: DoorEntity)
        fun onMonsterEntity(entity: MonsterEntity)
    }

    var entitiesInRoom = arrayOf<MutableList<AEntity>>()
    var entitiesStackID = 0
    var entityIDInStack = 0

    fun onAction()
    {
        var gameEntity = entitiesInRoom[entitiesStackID][entityIDInStack]
        if(gameEntity is ChestEntity)
            callbacks.onChestEntity(gameEntity as ChestEntity)
        else if (gameEntity is DoorEntity)
            callbacks.onDoorEntity(gameEntity as DoorEntity)
        else if (gameEntity is MonsterEntity)
            callbacks.onMonsterEntity(gameEntity as MonsterEntity)
    }

}