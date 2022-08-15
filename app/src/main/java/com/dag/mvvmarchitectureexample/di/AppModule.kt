package com.dag.mvvmarchitectureexample.di

import android.app.Application
import android.content.Context
import com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import com.dag.mvvmarchitectureexample.datastore.proto.ProtoDataStoreImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    fun provideContext(application: Application) = application.applicationContext

    @Singleton
    @Provides
    fun providePreferencesDatastore(context:Context) = PreferencesDataStore(context)

    @Singleton
    @Provides
    fun provideProtoDatastore(context: Context) = ProtoDataStoreImpl(context)

}