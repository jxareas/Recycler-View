package com.jonareas.taxer.view.worker.adapter

import androidx.recyclerview.selection.ItemKeyProvider

class WorkerKeyProvider(private val adapter : WorkerAdapter) : ItemKeyProvider<Long>(SCOPE_CACHED) {
    override fun getKey(position: Int): Long =
        adapter.currentList[position].workerId

    override fun getPosition(key: Long): Int =
        adapter.currentList.indexOfFirst { it.workerId == key }
}
