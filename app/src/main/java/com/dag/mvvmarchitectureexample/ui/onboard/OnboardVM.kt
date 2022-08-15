package com.dag.mvvmarchitectureexample.ui.onboard

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.dag.mvvmarchitectureexample.base.BaseVM
import com.dag.mvvmarchitectureexample.data.Onboard
import com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import com.dag.mvvmarchitectureexample.datastore.proto.ProtoDataStoreImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnboardVM @Inject constructor(
    var protoDataStore: ProtoDataStoreImpl
):BaseVM() {

    fun onBoardIsShown() = viewModelScope.launch{
        protoDataStore.changeOnboardSkipped(true)
    }

    fun writeOnboardType(result: Onboard.OnboardType = Onboard.OnboardType.NONE)
    = viewModelScope.launch{
        protoDataStore.changeFilter(result)
    }

}