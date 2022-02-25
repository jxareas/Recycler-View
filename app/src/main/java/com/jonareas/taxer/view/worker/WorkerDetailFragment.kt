package com.jonareas.taxer.view.worker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonareas.taxer.R
import com.jonareas.taxer.databinding.FragmentWorkerDetailBinding

class WorkerDetailFragment : Fragment() {

    private var _binding : FragmentWorkerDetailBinding? = null
    private val binding : FragmentWorkerDetailBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}