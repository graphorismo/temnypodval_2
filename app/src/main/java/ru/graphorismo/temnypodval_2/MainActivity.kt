package ru.graphorismo.temnypodval_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.graphorismo.temnypodval_2.databinding.ActivityMainBinding
import ru.graphorismo.temnypodval_2.model.AEntity
import ru.graphorismo.temnypodval_2.model.GameLogic

class MainActivity : AppCompatActivity(), ButtonsFragment.ICallbacks {

    private val viewModel : MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onMenuFragmentOpen() {
        val menuFragment = MenuFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activityMain_fragmentContainerViewMain, menuFragment)
            .commit()
    }

}