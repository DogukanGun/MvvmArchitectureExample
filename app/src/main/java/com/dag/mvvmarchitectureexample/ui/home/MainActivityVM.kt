package com.dag.mvvmarchitectureexample.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dag.mvvmarchitectureexample.base.BaseVM

class MainActivityVM:BaseVM() {

    fun changeState(){
        state.postValue(MainActivityVS.StartState)
    }
}