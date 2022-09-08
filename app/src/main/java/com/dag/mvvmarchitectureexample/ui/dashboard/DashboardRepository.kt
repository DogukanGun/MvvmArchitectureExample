package com.dag.mvvmarchitectureexample.ui.dashboard

class DashboardRepository {

    fun getDashboardItems():List<DashboardItem> = Feature.values().map { DashboardItem(it.name) }
}