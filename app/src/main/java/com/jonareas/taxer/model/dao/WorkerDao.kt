package com.jonareas.taxer.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.jonareas.taxer.model.entity.Worker
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkerDao : BaseDao<Worker> {

    @Query("SELECT * FROM Worker")
    override fun getAll(): Flow<List<Worker>>

    @Query("SELECT * FROM Worker where workerId = :id")
    override fun getById(id: Long): Flow<Worker>

}
