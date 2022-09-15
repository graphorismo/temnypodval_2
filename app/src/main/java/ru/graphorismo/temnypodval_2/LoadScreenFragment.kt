package ru.graphorismo.temnypodval_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.graphorismo.temnypodval_2.databinding.FragmentButtonsBinding
import ru.graphorismo.temnypodval_2.databinding.FragmentLoadScreenBinding

private const val ARG_LOADING_HEADER = "load screen heading"

/**
 * A simple [Fragment] subclass.
 * Use the [LoadScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoadScreenFragment : Fragment() {

    private var _binding: FragmentLoadScreenBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private var loadingHeader: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            loadingHeader = it.getString(ARG_LOADING_HEADER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentLoadingScreenTextViewHeader.setText(loadingHeader)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(heading: String) =
            LoadScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOADING_HEADER, heading)
                }
            }
    }
}