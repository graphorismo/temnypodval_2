package ru.graphorismo.temnypodval_2.model

import ru.graphorismo.temnypodval_2.R
import ru.graphorismo.temnypodval_2.model.data.EntityData
import ru.graphorismo.temnypodval_2.model.data.EntityType
import ru.graphorismo.temnypodval_2.model.data.PodvalRepository

class GameLogic()
{
    private lateinit var entitiesInRoom: MutableList<MutableList<AEntity?>>
    private var stackIDInRoom = 0
    private var entityIDInStack = 0
    private var player: PlayerEntity = PlayerEntity(0,100, 0)

    private var observersOfPlayerData = mutableListOf<IPlayerDataObserver>()
    private var observersOfEntityData = mutableListOf<IEntityDataObserver>()


    interface IPlayerDataObserver{
        fun onPlayerDataChange(newPlayerData: PlayerEntity)
    }

    interface IEntityDataObserver{
        fun onEntityDataChange(newEntityData: AEntity)
    }

    init {
        onRestart()
    }

    fun addPlayerDataObserver(observer: IPlayerDataObserver) = observersOfPlayerData.add(observer)
    private fun runPlayerDataObservers() = observersOfPlayerData.forEach() {it.onPlayerDataChange(getPlayerData())}
    fun removePlayerDataObserver(observer: IPlayerDataObserver) = observersOfPlayerData.remove(observer)

    fun addEntityDataObserver(observer: IEntityDataObserver) = observersOfEntityData.add(observer)
    private fun runEntityDataObservers() = observersOfEntityData.forEach() {it.onEntityDataChange(getCurrentEntityData())}
    fun removeEntityDataObserver(observer: IEntityDataObserver) = observersOfEntityData.remove(observer)


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
        runPlayerDataObservers()
        entitiesInRoom[stackIDInRoom].remove(chest)
        onSwitchNext()
    }

    private fun makePlayerAndMonsterHitEachOther(monster: MonsterEntity) {
            player.health -= monster.health/10
            runPlayerDataObservers()
            monster.health -= player.health/10
            runEntityDataObservers()
            if (monster.health <= 0) handleMonsterDeath(monster)
            if (player.health <= 0) handlePlayerDeath()
    }

    private fun handlePlayerDeath() {
        player = PlayerEntity(0,100,0)
        runPlayerDataObservers()
        generateNewRoomWithDifficulty(1)
    }

    private fun handleMonsterDeath(monster: AEntity) {
        player.score += 1
        runPlayerDataObservers()
        entitiesInRoom.forEach {
            if (it.contains(monster))
                it.remove(monster)
        }
        entityIDInStack = entitiesInRoom[stackIDInRoom].size-1
        runEntityDataObservers()
    }

    private fun generateNewRoomWithDifficulty(difficult: Int) {
        entitiesInRoom = mutableListOf<MutableList<AEntity?>>()
        entitiesInRoom.add(mutableListOf<AEntity?>())
        entitiesInRoom[0].add(DoorEntity(difficult+1, R.drawable.door))
        for (i in 0 until difficult+1){
            entitiesInRoom[0].add(MonsterEntity("Monster $i", 50*difficult, R.drawable.skeleton))
        }
        for (i in 1 until difficult+3){
            entitiesInRoom.add(mutableListOf<AEntity?>())
            entitiesInRoom[i].add(ChestEntity(difficult*100,R.drawable.chest))
            for (j in 0 until difficult+1){
                entitiesInRoom[i].add(MonsterEntity("Monster $i$j", 50*difficult, R.drawable.skeleton))
            }
        }
        stackIDInRoom=0
        entityIDInStack = entitiesInRoom[stackIDInRoom].size-1
        runEntityDataObservers()
    }

    fun getCurrentEntityData(): AEntity {
        return entitiesInRoom[stackIDInRoom][entityIDInStack]?.clone() ?:
            throw Exception("Current entitty cant be a null")
    }

    fun onSwitchNext() {
        if (entitiesInRoom.size == 0)
            throw Exception("Room cant be empty")
        stackIDInRoom+=1
        var roomIsOver: Boolean = stackIDInRoom>=entitiesInRoom.size
        if (roomIsOver) stackIDInRoom = 0
        entityIDInStack = entitiesInRoom[stackIDInRoom].size-1
        runEntityDataObservers()

    }

    fun onSwitchPrev() {
        if (entitiesInRoom.size == 0)
            throw Exception("Room cant be empty")
        stackIDInRoom-=1
        var roomIsOver: Boolean = stackIDInRoom<0
        if (roomIsOver) stackIDInRoom = entitiesInRoom.size-1
        entityIDInStack = entitiesInRoom[stackIDInRoom].size-1
        runEntityDataObservers()
    }

    fun getPlayerData(): PlayerEntity {
        return player.clone() as PlayerEntity
    }

    fun onRestart() {
        player = PlayerEntity(0, 100, 0)
        runPlayerDataObservers()
        generateNewRoomWithDifficulty(1);
    }

    fun updateObserversData(){
        runPlayerDataObservers()
        runEntityDataObservers()
    }

    fun convertDataToDataBaseReadyFormat(): MutableList<EntityData>{
        var convertedData = mutableListOf<EntityData>()
        convertedData.add(
            EntityData(
                type = EntityType.PLAYER.value,
                health = player.health,
                score = player.score
            )
        )
        entitiesInRoom.forEach(){ stack ->
            stack.forEach(){
                if(it is ChestEntity){
                    convertedData.add(
                        EntityData(
                            type = EntityType.CHEST.value,
                            restoration = it.healthRestore,
                            pictureId = it.imageId,
                            stackId = entitiesInRoom.indexOf(stack),
                            positionId = stack.indexOf(it)
                        )
                    )
                }
                else if (it is DoorEntity){
                    convertedData.add(
                        EntityData(
                            type = EntityType.DOOR.value,
                            difficulty = it.difficult,
                            pictureId = it.imageId,
                            stackId = entitiesInRoom.indexOf(stack),
                            positionId = stack.indexOf(it)
                        )
                    )
                }
                else if (it is MonsterEntity){
                    convertedData.add(
                        EntityData(
                            type = EntityType.MONSTER.value,
                            name = it.name,
                            health = it.health,
                            pictureId = it.imageId,
                            stackId = entitiesInRoom.indexOf(stack),
                            positionId = stack.indexOf(it)
                        )
                    )
                }
            }

        }
        return convertedData
    }

    fun loadFromDataBaseData(data: MutableList<EntityData>){
        entitiesInRoom.clear()
        data.forEach(){
            if (it.type==EntityType.PLAYER.value){
                player =
                    PlayerEntity(
                        score=it.score,
                        health=it.health,
                        imageId = 0
                    )
                runPlayerDataObservers()
            }
            else if (it.type==EntityType.DOOR.value){
                var door =
                    DoorEntity(
                        difficult = it.difficulty,
                        imageId = it.pictureId)
                extendEntitiesToPosition(it.stackId, it.positionId)
                entitiesInRoom[it.stackId][it.positionId]=door
            }
            else if (it.type==EntityType.CHEST.value){
                var chest =
                    ChestEntity(
                        healthRestore = it.restoration,
                        imageId = it.pictureId)
                extendEntitiesToPosition(it.stackId, it.positionId)
                entitiesInRoom[it.stackId][it.positionId]=chest
            }
            else if (it.type==EntityType.MONSTER.value){
                var monster =
                    MonsterEntity(
                        name=it.name,
                        health = it.health,
                        imageId = it.pictureId)
                extendEntitiesToPosition(it.stackId, it.positionId)
                entitiesInRoom[it.stackId][it.positionId]= monster
            }
        }
        onSwitchNext()
        onSwitchPrev()
    }

    fun extendEntitiesToPosition(stackid: Int, posid: Int){
        val numberOfStacks = stackid+1 -  entitiesInRoom.size
        if (numberOfStacks>0)
            repeat(numberOfStacks){
                entitiesInRoom.add(mutableListOf())
            }
        val numberOfPos = posid+1 - entitiesInRoom[stackid].size
        if (numberOfPos > 0)
            repeat(numberOfPos){
                entitiesInRoom[stackid].add(null)
            }

    }

    suspend fun saveToDB() {
        PodvalRepository.get().deleteAll()
        var data = this.convertDataToDataBaseReadyFormat()
        PodvalRepository.get().insertAll(data)
    }

    suspend fun loadFromDB() {
        var data = PodvalRepository.get().getAll()
        this.loadFromDataBaseData(data as MutableList<EntityData>)
    }

}