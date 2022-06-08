package com.jonareas.taxer.view.worker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.R.*
import com.google.android.material.transition.MaterialContainerTransform
import com.jonareas.taxer.R
import com.jonareas.taxer.databinding.FragmentWorkerDetailBinding
import com.jonareas.taxer.model.entity.Worker
import com.jonareas.taxer.utils.double
import com.jonareas.taxer.utils.string
import com.jonareas.taxer.viewmodel.WorkerDetailViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WorkerDetailFragment : Fragment() {

    private var _binding : FragmentWorkerDetailBinding? = null
    private val binding : FragmentWorkerDetailBinding
        get() = _binding!!

    private val viewModel : WorkerDetailViewModel by viewModels()
    private val navArgs : WorkerDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_main
            scrimColor = Color.TRANSPARENT
            duration = 500L
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkerDetailBinding.inflate(inflater, container, false)
        setupWorker()
        setupListener()
        return binding.root
    }

    private fun setupListener() : Unit = binding.run {
        buttonSave.setOnClickListener {
            viewModel.updateWorker(bindWorker())
            findNavController().navigateUp()
        }

        buttonDelete.setOnClickListener {
            viewModel.deleteWorker(worker!!)
            findNavController().navigateUp()
        }


    }

    private fun setupWorker() {
        lifecycle.coroutineScope.launch {
            viewModel.getWorker(navArgs.workerId).collect { worker ->
                binding.worker = worker
            }
        }
    }

    private fun bindWorker() : Worker = binding.run{
        Worker(editTextFirstName.string, editTextSurname.string, editTextSalary.double, navArgs.workerId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
