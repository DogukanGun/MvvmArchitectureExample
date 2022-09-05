package com.dag.mvvmarchitectureexample.ui.information

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import com.dag.mvvmarchitectureexample.base.BaseVM
import com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import kotlinx.coroutines.launch
import javax.inject.Inject

class InformationVM @Inject constructor(val preferencesDataStore: PreferencesDataStore):BaseVM(){

    fun <T> writeToPreferencesDatastore(key: Preferences.Key<T>, value:T) = viewModelScope.launch{
        preferencesDataStore.write(key,value)
    }

}