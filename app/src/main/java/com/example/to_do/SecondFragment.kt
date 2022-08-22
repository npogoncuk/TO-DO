package com.example.to_do

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.to_do.databinding.FragmentSecondBinding
import com.example.to_do.model.Note

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private val args: SecondFragmentArgs by navArgs()
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.text.setText(args.note?.text ?: "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.detail_note_menu, menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_save_note -> {
                if (binding.text.text.isNotEmpty()) {
                    val note = Note(text = binding.text.text.toString(), timeStamp = System.currentTimeMillis(), done = false)
                    if (args.note != null)
                        App.instance.noteDao.update(note)
                    else App.instance.noteDao.insert(note)
                }
                (requireActivity() as VisibleFab).makeFabVisible()
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    interface VisibleFab {
        fun makeFabVisible()
    }

}