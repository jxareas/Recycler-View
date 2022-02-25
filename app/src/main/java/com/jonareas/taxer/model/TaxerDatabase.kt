package com.jonareas.taxer.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jonareas.taxer.model.TaxerDatabase.Companion.DATABASE_VERSION
import com.jonareas.taxer.model.dao.WorkerDao
import com.jonareas.taxer.model.entity.Worker

@Database(entities = [Worker::class], version = DATABASE_VERSION)
abstract class TaxerDatabase : RoomDatabase() {

    abstract val workerDao : WorkerDao

    companion object {
        const val DATABASE_VERSION : Int = 1
        const val DATABASE_NAME : String = "taxer.db"

        @Volatile
        private var INSTANCE : TaxerDatabase? = null

        private fun buildDatabase(context : Context) : TaxerDatabase =
            Room
                .databaseBuilder(context, TaxerDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun database(context: Context) : TaxerDatabase =
            INSTANCE ?: synchronized(this) {
                buildDatabase(context)
            }.also { INSTANCE = it }


    }

}
