package com.jonareas.taxer.view.worker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jonareas.taxer.databinding.FragmentWorkerListBinding

class WorkerListFragment : Fragment() {

    private var _binding : FragmentWorkerListBinding? = null
    private val binding : FragmentWorkerListBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkerListBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}