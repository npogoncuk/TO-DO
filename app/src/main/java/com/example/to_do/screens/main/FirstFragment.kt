package com.example.to_do.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.to_do.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.list.adapter = Adapter(::makeObserveViewHolder)
        viewModel.notesLiveData.observe(viewLifecycleOwner) {
            (binding.list.adapter as Adapter).setItems(it)
        }
    }

    private fun makeObserveViewHolder(view: View) {
        viewModel.colorOfTasks.observe(viewLifecycleOwner) {
            view.background = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}