package com.jonareas.taxer.view.worker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonareas.taxer.databinding.FragmentWorkerListBinding
import com.jonareas.taxer.viewmodel.WorkerListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WorkerListFragment : Fragment() {

    private var _binding : FragmentWorkerListBinding? = null
    private val binding : FragmentWorkerListBinding
        get() = _binding!!

    private val viewModel : WorkerListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkerListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() : Unit = binding.fabAddWorker.setOnClickListener {
        findNavController().navigate(WorkerListFragmentDirections.workerListToAddWorker())
    }

    private fun setupRecyclerView() : Unit = binding.recyclerViewWorkerList.run {

        layoutManager = LinearLayoutManager(activity)
        val workerAdapter = WorkerAdapter {
            findNavController().navigate(WorkerListFragmentDirections.workerListToWorkerDetail())
        }
        adapter = workerAdapter

        suscribeAdapter(workerAdapter)


    }

    private fun suscribeAdapter(adapter: WorkerAdapter) {
        lifecycle.coroutineScope.launch {
            viewModel.workers.collect {
                adapter.submitList(it)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
