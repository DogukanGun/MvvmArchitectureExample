package com.dag.mvvmarchitectureexample.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseVM:ViewModel() {
    val state = MutableLiveData<BaseVS>()
}