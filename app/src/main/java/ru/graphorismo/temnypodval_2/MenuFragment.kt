package ru.graphorismo.temnypodval_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.graphorismo.temnypodval_2.databinding.FragmentMenuBinding
import kotlin.system.exitProcess


class MenuFragment : Fragment() {

    interface ICallbacks{
        fun onMenuFragmentClose()
    }

    private var _binding: FragmentMenuBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentMenuButtonResume.setOnClickListener() {
            var callbacks = activity as ICallbacks
            callbacks.onMenuFragmentClose()
        }

        binding.fragmentButtonsButtonRestart.setOnClickListener() {
            viewModel.gameLogic.onRestart()
            var callbacks = activity as ICallbacks
            callbacks.onMenuFragmentClose()
        }

        binding.fragmentMenuButtonExit.setOnClickListener(){
            exitProcess(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}