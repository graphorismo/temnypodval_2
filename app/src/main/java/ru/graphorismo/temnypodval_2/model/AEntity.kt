package ru.graphorismo.temnypodval_2.model

abstract class AEntity(var imageId:Int) {
    val id: Int by lazy { hashCode() }

}