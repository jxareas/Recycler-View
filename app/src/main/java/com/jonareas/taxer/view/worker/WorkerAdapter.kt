package com.jonareas.taxer.view.worker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jonareas.taxer.databinding.ListItemWorkerBinding
import com.jonareas.taxer.model.entity.Worker
import com.jonareas.taxer.utils.OnViewHolderClick

class WorkerAdapter(private val listener : OnViewHolderClick<Worker>) :
    ListAdapter<Worker, WorkerAdapter.WorkerViewHolder>(DiffCallback) {

    private companion object DiffCallback : DiffUtil.ItemCallback<Worker>() {
        override fun areItemsTheSame(oldItem: Worker, newItem: Worker): Boolean =
            oldItem.workerId == newItem.workerId

        override fun areContentsTheSame(oldItem: Worker, newItem: Worker): Boolean =
            oldItem.fullName == newItem.fullName && oldItem.salary == newItem.salary

    }

    inner class WorkerViewHolder(private val binding : ListItemWorkerBinding) :
        RecyclerView.ViewHolder(binding.root) {

            init {
                itemView.setOnClickListener { listener.onClick(getItem(bindingAdapterPosition)) }
            }

            fun bind(worker : Worker) : Unit = binding.run {
                textViewFullName.text = worker.fullName
                textViewSalary.text = worker.salary.toString()
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder =
        ListItemWorkerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::WorkerViewHolder)

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) : Unit =
        holder.bind(getItem(position))


}
