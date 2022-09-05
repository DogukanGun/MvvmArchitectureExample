package com.dag.mvvmarchitectureexample.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.base.BaseVS
import com.dag.mvvmarchitectureexample.databinding.ActivityMainBinding
import com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import com.dag.mvvmarchitectureexample.ui.information.InformationActivity
import com.dag.mvvmarchitectureexample.ui.onboard.OnboardActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityVM, ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getLayoutViewModel(): MainActivityVM = mainActivityVM

    @Inject
    lateinit var mainActivityVM: MainActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel?.isOnboardShown()
    }

    private fun handleNextPageStatus(isActive:Boolean){
        if (isActive) startActivity(InformationActivity::class.java)
        else viewModel?.changeState()
    }

    private fun handleOnboardStatus(isActive: Boolean){
        if (isActive){
            startActivity(OnboardActivity::class.java)
        }else{
            viewModel?.isNextActive()
        }
    }

    override fun stateChange(state: BaseVS?) {
        when (state) {
            MainActivityVS.StartState -> {
                Toast.makeText(this, "Text is changed.", Toast.LENGTH_LONG).show()
            }
            is MainActivityVS.NextPageStatus -> {
                handleNextPageStatus(state.active)
            }
            is MainActivityVS.OnboardStatus -> {
                handleOnboardStatus(state.active)
            }
        }
    }

}