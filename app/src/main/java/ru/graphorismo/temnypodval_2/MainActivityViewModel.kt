package ru.graphorismo.temnypodval_2

import androidx.lifecycle.ViewModel
import ru.graphorismo.temnypodval_2.model.ChestEntity
import ru.graphorismo.temnypodval_2.model.DoorEntity
import ru.graphorismo.temnypodval_2.model.GameLogic
import ru.graphorismo.temnypodval_2.model.MonsterEntity

class MainActivityViewModel : ViewModel(){
    var gameLogic = GameLogic()
}