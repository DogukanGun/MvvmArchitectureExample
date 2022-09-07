package com.dag.mvvmarchitectureexample.ui.information

class InformationRepository {

    fun getInformationItemList(): List<InformationItem> =
        InformationTypes.values().map { InformationItem(it.name) }
}