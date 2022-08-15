package com.dag.mvvmarchitectureexample.datastore.proto

import com.dag.mvvmarchitectureexample.data.Onboard
import kotlinx.coroutines.flow.Flow

interface ProtoStore {

    val filtersFlow: Flow<Onboard>

    suspend fun changeFilter(onboard:Onboard.OnboardType)

    suspend fun changeOnboardSkipped(enable: Boolean)
}