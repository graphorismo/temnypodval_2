package ru.graphorismo.temnypodval_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.graphorismo.temnypodval_2.databinding.FragmentInfoBinding
import ru.graphorismo.temnypodval_2.model.AEntity
import ru.graphorismo.temnypodval_2.model.GameLogic
import ru.graphorismo.temnypodval_2.model.PlayerEntity


class InfoFragment : Fragment(), GameLogic.IEntityDataObserver, GameLogic.IPlayerDataObserver {

    private var _binding: FragmentInfoBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)

        viewModel.gameLogic.addEntityDataObserver(this)
        viewModel.gameLogic.addPlayerDataObserver(this)

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.gameLogic.removeEntityDataObserver(this)
        viewModel.gameLogic.removePlayerDataObserver(this)

        _binding = null
    }

    override fun onPlayerDataChange(newPlayerData: PlayerEntity) {
        binding.fragmentInfoTextViewPlayerMain.setText(newPlayerData.getMainInfo())
        binding.fragmentInfoTextViewPlayerSupp.setText(newPlayerData.getSupportInfo())
    }

    override fun onEntityDataChange(newEntityData: AEntity) {
        binding.fragmentInfoTextViewEntityMain.setText(newEntityData.getMainInfo())
        binding.fragmentInfoTextViewEntitySupp.setText(newEntityData.getSupportInfo())
    }


}