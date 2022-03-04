package com.jonareas.taxer.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "Worker")
data class Worker(
    @NotNull
    val firstName : String,
    @NotNull
    val surname : String,
    @ColumnInfo(name = "salary") @NotNull
    val salary : Double,
    @PrimaryKey(autoGenerate = true) @NotNull
    val workerId : Int = 0
) :  Persistable {

    val fullName : String
        get() = "$firstName $surname"

    val taxes : Double
        get() = salary * DEFAULT_TAX_RATE

    val netSalary : Double
        get() = salary - taxes

    companion object {
        const val DEFAULT_TAX_RATE : Double = .07
    }


}
