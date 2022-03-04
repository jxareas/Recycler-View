package com.jonareas.taxer.view.worker.adapter

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class WorkerDetailsLookup(private val recyclerView : RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {

        val view = recyclerView.findChildViewUnder(event.x, event.y)
        return if(view != null)
            (recyclerView.findContainingViewHolder(view) as WorkerAdapter.WorkerViewHolder).getItem()
        else null

    }

}
