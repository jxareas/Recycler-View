package com.jonareas.taxer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonareas.taxer.TaxerApplication
import com.jonareas.taxer.model.entity.Worker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkerDetailViewModel : ViewModel() {

    private val workerDao = TaxerApplication.database.workerDao

    fun getWorker(id : Long) = workerDao.getById(id)

    fun updateWorker(worker : Worker) {
        viewModelScope.launch(Dispatchers.IO) {
           workerDao.update(worker)
        }
    }

    fun deleteWorker(worker: Worker) {
        viewModelScope.launch(Dispatchers.IO) {
            workerDao.delete(worker)
        }
    }



}
