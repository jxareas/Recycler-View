package com.jonareas.taxer

import android.app.Application
import com.jonareas.taxer.model.TaxerDatabase

class TaxerApplication : Application() {

    companion object {
        lateinit var INSTANCE : TaxerApplication private set

        val database by lazy {
            TaxerDatabase.database(INSTANCE)
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }



}
