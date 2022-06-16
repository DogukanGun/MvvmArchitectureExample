package com.dag.mvvmarchitectureexample.ui.home

import android.os.Bundle
import android.widget.Toast
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.base.BaseVS
import com.dag.mvvmarchitectureexample.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityVM,ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getLayoutViewModel(): MainActivityVM = mainActivityVM

    @Inject
    lateinit var mainActivityVM: MainActivityVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel?.changeState()
    }

    override fun stateChange(state: BaseVS?) {
        when(state){
            MainActivityVS.StartState ->{
                Toast.makeText(this,"Text is changed.",Toast.LENGTH_LONG).show()
            }
        }
    }

}