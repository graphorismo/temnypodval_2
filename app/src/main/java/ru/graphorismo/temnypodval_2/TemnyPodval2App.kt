package ru.graphorismo.temnypodval_2

import android.app.Application
import ru.graphorismo.temnypodval_2.model.data.PodvalRepository

class TemnyPodval2App : Application() {

    override fun onCreate() {
        super.onCreate()
        PodvalRepository.init(this)
    }
}