package ru.graphorismo.temnypodval_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.graphorismo.temnypodval_2.databinding.ActivityMainBinding
import ru.graphorismo.temnypodval_2.model.AEntity
import ru.graphorismo.temnypodval_2.model.GameLogic

class MainActivity : AppCompatActivity() {

    private val viewModel : MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        var gameLogic = viewModel.gameLogic

        binding.buttonInteract.setOnClickListener()
        {
            gameLogic.onInteraction()
            updateTextViewsForGameLogic(gameLogic)
        }

        binding.buttonNext.setOnClickListener(){
            gameLogic.onSwitchNext()
            updateTextViewsForGameLogic(gameLogic)
        }

        binding.buttonPrev.setOnClickListener()
        {
            gameLogic.onSwitchPrev()
            updateTextViewsForGameLogic(gameLogic)
        }

        updateTextViewsForGameLogic(gameLogic)
        setContentView(binding.root)
    }

    fun updateTextViewsForGameLogic(logic: GameLogic)
    {
        var entity : AEntity = logic.getCurrentEntity()
        binding.imageViewEntity.setImageResource(entity.imageId)
        binding.textViewMain.setText(entity.getMainInfo())
        binding.textViewSupport.setText(entity.getSupportInfo())
    }
}