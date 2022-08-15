package com.dag.mvvmarchitectureexample.ui.home

import androidx.lifecycle.viewModelScope
import com.dag.mvvmarchitectureexample.base.BaseVM
import com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import com.dag.mvvmarchitectureexample.datastore.proto.ProtoDataStoreImpl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityVM @Inject constructor(
    var preferencesDataStore: PreferencesDataStore,
    var protoDataStore: ProtoDataStoreImpl
):BaseVM() {

    fun isOnboardShown() = viewModelScope.launch{
        protoDataStore.filtersFlow.collect {
            state.postValue(MainActivityVS.OnboardStatus(it.isOnboardSkipped==0))
        }
    }

    fun changeState(){
        state.postValue(MainActivityVS.StartState)
    }

    fun isNextActive() = viewModelScope.launch {
        preferencesDataStore.read(PreferencesDataStore.INFORMATION_SCREEN).collect {
            state.postValue(MainActivityVS.NextPageStatus(it ?: false))
        }
    }

}