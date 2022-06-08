package com.jonareas.taxer.view.worker

import android.os.Bundle
import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.R.integer.material_motion_duration_long_2
import com.google.android.material.transition.MaterialElevationScale
import com.jonareas.taxer.R
import com.jonareas.taxer.databinding.FragmentWorkerListBinding
import com.jonareas.taxer.view.MainActivity
import com.jonareas.taxer.view.worker.adapter.WorkerAdapter
import com.jonareas.taxer.view.worker.adapter.WorkerDetailsLookup
import com.jonareas.taxer.view.worker.adapter.WorkerKeyProvider
import com.jonareas.taxer.viewmodel.WorkerListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WorkerListFragment : Fragment(), ActionMode.Callback {

    private var _binding: FragmentWorkerListBinding? = null
    private val binding: FragmentWorkerListBinding
        get() = _binding!!

    private val viewModel: WorkerListViewModel by viewModels()
    private lateinit var workerAdapter: WorkerAdapter

    private lateinit var tracker: SelectionTracker<Long>
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(material_motion_duration_long_2).toLong()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWorkerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        workerAdapter = WorkerAdapter { worker ->
            val transitionName = resources.getString(R.string.worker_transition_name)
            val extras = FragmentNavigatorExtras(
                binding.recyclerViewWorkerList to transitionName
            )
            val direction = WorkerListFragmentDirections.workerListToWorkerDetail(worker.fullName,
                worker.workerId)
            findNavController().navigate(direction, extras)
        }
        setupRecyclerView()
        setupTracker()
        setupListeners()
    }

    private fun setupTracker() {
        val recyclerView = binding.recyclerViewWorkerList

        tracker = SelectionTracker.Builder("SELECTION", recyclerView,
            WorkerKeyProvider(workerAdapter), WorkerDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage())
            .withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()

        tracker.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onItemStateChanged(key: Long, selected: Boolean) {
                    super.onItemStateChanged(key, selected)

                    if (actionMode == null) {
                        actionMode =
                            (activity as MainActivity).startSupportActionMode(this@WorkerListFragment)
                    }

                    val itemSelected = tracker.selection.size()

                    actionMode?.apply {
                        if (itemSelected > 0)
                            title = getString(R.string.action_selected, itemSelected)
                        else finish()
                    }

                }
            }
        )

        workerAdapter.tracker = tracker

    }

    private fun setupListeners(): Unit = binding.fabAddWorker.setOnClickListener {
            val transitionName = resources.getString(R.string.fab_transition_name)
            val extra = FragmentNavigatorExtras(
                binding.fabAddWorker to transitionName
            )
            val direction = WorkerListFragmentDirections.workerListToAddWorker()
            findNavController().navigate(direction, extra)

        }

        private fun setupRecyclerView(): Unit = binding.recyclerViewWorkerList.run {

            layoutManager = LinearLayoutManager(activity)
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

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.menu_actions, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = true

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean =
            when (item!!.itemId) {
                R.id.action_delete -> {

                    workerAdapter
                        .currentList
                        .filter { tracker.selection.contains(it.workerId) }
                        .toTypedArray()
                        .let(viewModel::deleteWorkers)


                    true
                }
                else -> false
            }

        override fun onDestroyActionMode(mode: ActionMode?) {
            tracker.clearSelection()
            actionMode = null

        }

    }
