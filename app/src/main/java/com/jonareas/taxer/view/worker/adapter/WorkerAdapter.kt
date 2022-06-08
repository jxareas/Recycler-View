package com.jonareas.taxer.view.worker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jonareas.taxer.R
import com.jonareas.taxer.databinding.ListItemWorkerBinding
import com.jonareas.taxer.model.entity.Worker
import com.jonareas.taxer.utils.OnViewHolderClick

class WorkerAdapter(private val listener : OnViewHolderClick<Worker>) :
    ListAdapter<Worker, WorkerAdapter.WorkerViewHolder>(DiffCallback) {

    var tracker : SelectionTracker<Long>? = null

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

                val transitionName = root.context.getString(R.string.worker_transition_name)
                ViewCompat.setTransitionName(root, "$transitionName$itemId}")

                textViewFullName.text = worker.fullName
                textViewSalary.text = worker.salary.toString()

                tracker?.let {
                    if(it.isSelected(worker.workerId))
                        root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.alice_blue))
                    else root.background = null
                }

            }

        fun getItem(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {

                override fun getPosition(): Int = bindingAdapterPosition

                override fun getSelectionKey(): Long = currentList[bindingAdapterPosition].workerId

            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder =
        ListItemWorkerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::WorkerViewHolder)

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) : Unit =
        holder.bind(getItem(position))


}
