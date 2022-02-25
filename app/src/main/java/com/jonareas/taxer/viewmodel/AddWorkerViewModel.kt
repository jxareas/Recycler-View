package com.jonareas.taxer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonareas.taxer.TaxerApplication
import com.jonareas.taxer.model.entity.Worker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWorkerViewModel : ViewModel() {

    private val workerDao = TaxerApplication.database.workerDao

    fun addWorker(worker : Worker) {
        viewModelScope.launch(Dispatchers.IO) {
            workerDao.insert(worker)
        }
    }

}
