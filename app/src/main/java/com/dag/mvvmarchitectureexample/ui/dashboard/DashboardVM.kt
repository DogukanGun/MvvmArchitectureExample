package com.dag.mvvmarchitectureexample.ui.dashboard

import com.dag.mvvmarchitectureexample.base.BaseVM

class DashboardVM:BaseVM() {

    private val dashboardRepository = DashboardRepository()

    fun getDashboardItems():List<DashboardItem> = dashboardRepository.getDashboardItems()

}