package com.dag.mvvmarchitectureexample.ui.information

import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.databinding.ActivityInformationBinding
import javax.inject.Inject

class InformationActivity:BaseActivity<InformationVM,ActivityInformationBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_information

    override fun getLayoutViewModel(): InformationVM = informationVM

    @Inject
    lateinit var informationVM: InformationVM
}