package com.dag.mvvmarchitectureexample.di

import com.dag.mvvmarchitectureexample.ui.home.MainActivityVM
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {

    @Provides
    fun provideMainActivityVM() = MainActivityVM()
}