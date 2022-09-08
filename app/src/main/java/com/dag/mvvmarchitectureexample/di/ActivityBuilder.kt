package com.dag.mvvmarchitectureexample.di

import com.dag.mvvmarchitectureexample.ui.calculation.CalculationActivity
import com.dag.mvvmarchitectureexample.ui.dashboard.DashboardActivity
import com.dag.mvvmarchitectureexample.ui.home.MainActivity
import com.dag.mvvmarchitectureexample.ui.information.InformationActivity
import com.dag.mvvmarchitectureexample.ui.onboard.OnboardActivity
import com.dag.mvvmarchitectureexample.ui.qr.QrFeatureActivity
import com.dag.mvvmarchitectureexample.ui.sms.SmsActivity
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

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun provideDashboardActivity(): DashboardActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun provideSmsActivity(): SmsActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun provideQrActivity(): QrFeatureActivity
}