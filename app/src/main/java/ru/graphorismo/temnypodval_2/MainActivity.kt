package ru.graphorismo.temnypodval_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.graphorismo.temnypodval_2.databinding.ActivityMainBinding
import ru.graphorismo.temnypodval_2.model.AEntity
import ru.graphorismo.temnypodval_2.model.GameLogic

class MainActivity : AppCompatActivity(),
    ButtonsFragment.ICallbacks,
    MenuFragment.ICallbacks{

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

    override fun onMenuFragmentClose() {
        val mainFragment = MainFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activityMain_fragmentContainerViewMain, mainFragment)
            .commit()
    }

    override fun onLoadButtonClick() {
        var loadScreenFragment = LoadScreenFragment.newInstance("Loading")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activityMain_fragmentContainerViewMain, loadScreenFragment)
            .commit()

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.gameLogic.loadFromDB()

            val mainFragment = MainFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activityMain_fragmentContainerViewMain, mainFragment)
                .commit()
        }
    }

    override fun onSaveButtonClick() {
        var loadScreenFragment = LoadScreenFragment.newInstance("Saving")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activityMain_fragmentContainerViewMain, loadScreenFragment)
            .commit()

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.gameLogic.saveToDB()

            val mainFragment = MainFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activityMain_fragmentContainerViewMain, mainFragment)
                .commit()
        }


    }

}