package com.jonareas.taxer.viewmodel

import androidx.lifecycle.ViewModel
import com.jonareas.taxer.TaxerApplication

class WorkerListViewModel : ViewModel() {

    private val workerDao = TaxerApplication.database.workerDao

    val workers = workerDao.getAll()

}
