package ru.graphorismo.temnypodval_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.graphorismo.temnypodval_2.databinding.FragmentPictureBinding
import ru.graphorismo.temnypodval_2.model.AEntity
import ru.graphorismo.temnypodval_2.model.GameLogic
import ru.graphorismo.temnypodval_2.model.PlayerEntity

class PictureFragment : Fragment(), GameLogic.IEntityDataObserver {
    private var _binding: FragmentPictureBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureBinding.inflate(inflater, container, false)

        viewModel.gameLogic.addEntityDataObserver(this)
        viewModel.gameLogic.updateObserversData()

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.gameLogic.removeEntityDataObserver(this)

        _binding = null
    }

    override fun onEntityDataChange(newEntityData: AEntity) {
        binding.fragmentPictureImageViewEntityPicture.setImageResource(newEntityData.imageId)
    }
}