package com.dag.mvvmarchitectureexample.di

import   com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import com.dag.mvvmarchitectureexample.datastore.proto.ProtoDataStoreImpl
import com.dag.mvvmarchitectureexample.ui.calculation.CalculationVM
import com.dag.mvvmarchitectureexample.ui.dashboard.DashboardVM
import com.dag.mvvmarchitectureexample.ui.home.MainActivityVM
import com.dag.mvvmarchitectureexample.ui.information.InformationVM
import com.dag.mvvmarchitectureexample.ui.onboard.OnboardVM
import com.dag.mvvmarchitectureexample.ui.qr.QrVM
import com.dag.mvvmarchitectureexample.ui.sms.SmsVM
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {

    @Provides
    fun provideMainActivityVM(preferencesDataStore: PreferencesDataStore,
                              protoDataStore: ProtoDataStoreImpl)
    = MainActivityVM(preferencesDataStore,protoDataStore)

    @Provides
    fun provideOnboardVM(protoDataStore: ProtoDataStoreImpl) = OnboardVM(protoDataStore)

    @Provides
    fun provideInformationVM(preferencesDataStore: PreferencesDataStore) = InformationVM(preferencesDataStore)

    @Provides
    fun provideCalculationVM() = CalculationVM()

    @Provides
    fun provideDashboardVM() = DashboardVM()

    @Provides
    fun provideSmsVM() = SmsVM()

    @Provides
    fun provideQrVM() = QrVM()
}