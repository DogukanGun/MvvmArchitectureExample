package com.dag.mvvmarchitectureexample.di

import com.dag.mvvmarchitectureexample.ui.home.MainActivityVM
import com.dag.mvvmarchitectureexample.ui.onboard.OnboardVM
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {

    @Provides
    fun provideMainActivityVM() = MainActivityVM()

    @Provides
    fun provideOnboardVM() = OnboardVM()
}