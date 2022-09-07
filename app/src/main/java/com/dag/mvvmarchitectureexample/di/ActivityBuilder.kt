package com.dag.mvvmarchitectureexample.di

import android.view.View
import com.dag.mvvmarchitectureexample.ui.calculation.CalculationActivity
import com.dag.mvvmarchitectureexample.ui.home.MainActivity
import com.dag.mvvmarchitectureexample.ui.information.InformationActivity
import com.dag.mvvmarchitectureexample.ui.onboard.OnboardActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun provideMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun provideOnboardActivity(): OnboardActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun provideInformationActivity(): InformationActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun provideCalculationActivity(): CalculationActivity
}