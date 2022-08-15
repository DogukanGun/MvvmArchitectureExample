package com.dag.mvvmarchitectureexample.datastore.proto

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dag.mvvmarchitectureexample.data.Onboard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ProtoDataStoreImpl @Inject constructor(val context: Context) : ProtoStore {

    val Context.protoDatastore: DataStore<Onboard> by dataStore(
        fileName = "onboard.pb",
        serializer = OnboardSerializer
    )

    override val filtersFlow: Flow<Onboard>
        get() = context.protoDatastore.data.catch { exception ->
            if (exception is IOException){
                exception.printStackTrace()
                emit(Onboard.getDefaultInstance())
            }else{
                throw exception
            }
        }

    override suspend fun changeFilter(onboard: Onboard.OnboardType) {
        context.protoDatastore.updateData { currentFilters ->
            currentFilters.toBuilder().setFilter(onboard).build()
        }
    }

    override suspend fun changeOnboardSkipped(enable: Boolean) {
        context.protoDatastore.updateData { currentFilters ->
            currentFilters.toBuilder().setIsOnboardSkipped(if (enable) 1 else 0).build()
        }
    }


}