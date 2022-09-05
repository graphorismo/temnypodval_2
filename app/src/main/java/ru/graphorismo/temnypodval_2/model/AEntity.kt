package ru.graphorismo.temnypodval_2.model

abstract class AEntity(var imageId:Int) {
    abstract fun getMainInfo(): String
    abstract fun getSupportInfo(): String

    val id: Int by lazy { hashCode() }

}