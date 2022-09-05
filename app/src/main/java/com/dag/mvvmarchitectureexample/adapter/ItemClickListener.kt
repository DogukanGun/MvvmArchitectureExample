package com.dag.mvvmarchitectureexample.adapter

import android.view.View
import androidx.viewbinding.ViewBinding

fun interface ItemClickListener<T> {

    fun onClick(position: Int, item: T)
}
