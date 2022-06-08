package com.jonareas.taxer.view.worker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.R.integer.material_motion_duration_long_2
import com.google.android.material.transition.MaterialContainerTransform
import com.jonareas.taxer.R
import com.jonareas.taxer.databinding.FragmentAddWorkerBinding
import com.jonareas.taxer.model.entity.Worker
import com.jonareas.taxer.utils.double
import com.jonareas.taxer.utils.string
import com.jonareas.taxer.viewmodel.AddWorkerViewModel


class AddWorkerFragment : Fragment() {

    private var _binding: FragmentAddWorkerBinding? = null
    private val binding: FragmentAddWorkerBinding
        get() = _binding!!

    private val viewModel: AddWorkerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = resources.getInteger(material_motion_duration_long_2).toLong()
            drawingViewId = R.id.nav_host_fragment_main
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddWorkerBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }


    private fun setupListeners(): Unit = binding.run {
        buttonSave.setOnClickListener {
            viewModel.addWorker(bindWorker())
            findNavController().navigateUp()
        }

        buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindWorker(): Worker = binding.run {
        Worker(editTextFirstName.string, editTextSurname.string, editTextSalary.double)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
