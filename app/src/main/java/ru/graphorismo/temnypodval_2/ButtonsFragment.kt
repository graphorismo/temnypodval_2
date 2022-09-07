package ru.graphorismo.temnypodval_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.graphorismo.temnypodval_2.databinding.FragmentButtonsBinding


class ButtonsFragment : Fragment() {

    private var _binding: FragmentButtonsBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentButtonsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentButtonsButtonNext.setOnClickListener() {viewModel.gameLogic.onSwitchNext()}
        binding.fragmentButtonsButtonPrev.setOnClickListener() {viewModel.gameLogic.onSwitchPrev()}
        binding.fragmentButtonsButtonInteract.setOnClickListener() {viewModel.gameLogic.onInteraction()}
        binding.fragmentButtonsButtonRestart.setOnClickListener() {viewModel.gameLogic.onRestart()}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}