package com.jonareas.taxer.utils

import android.widget.EditText
import com.jonareas.taxer.model.entity.Persistable

@FunctionalInterface fun interface OnViewHolderClick<T : Persistable> {
    fun onClick(holder : T)
}

val EditText.string : String get() = text.toString()

val EditText.double : Double get() = string.toDouble()
