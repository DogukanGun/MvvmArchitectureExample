package com.dag.mvvmarchitectureexample.ui.home

import com.dag.mvvmarchitectureexample.base.BaseVS

sealed class MainActivityVS: BaseVS {
    object StartState:MainActivityVS()
    class OnboardStatus(val active:Boolean):MainActivityVS()
    class NextPageStatus(val active:Boolean):MainActivityVS()
}