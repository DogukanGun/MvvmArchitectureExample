package com.dag.mvvmarchitectureexample.di

import com.dag.mvvmarchitectureexample.ui.home.MainActivity
import com.dag.mvvmarchitectureexample.ui.onboard.OnboardActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun provideMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun provideOnboardActivity(): OnboardActivity
}