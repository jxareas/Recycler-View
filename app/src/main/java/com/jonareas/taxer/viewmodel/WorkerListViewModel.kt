package com.jonareas.taxer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonareas.taxer.TaxerApplication
import com.jonareas.taxer.model.entity.Worker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkerListViewModel : ViewModel() {

    private val workerDao = TaxerApplication.database.workerDao

    val workers = workerDao.getAll()

    fun deleteWorkers(vararg workers : Worker) {
        viewModelScope.launch(Dispatchers.IO) {
            workerDao.delete(*workers)
        }
    }

}
