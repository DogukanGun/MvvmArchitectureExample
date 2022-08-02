package com.dag.mvvmarchitectureexample.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.base.BaseVS
import com.dag.mvvmarchitectureexample.databinding.ActivityMainBinding
import com.dag.mvvmarchitectureexample.datastore.preferences.DataStorePreferencesKey
import com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import com.dag.mvvmarchitectureexample.ui.onboard.OnboardActivity
import com.dag.mvvmarchitectureexample.ui.onboard.OnboardVM
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityVM, ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getLayoutViewModel(): MainActivityVM = mainActivityVM

    @Inject
    lateinit var mainActivityVM: MainActivityVM

    @Inject
    lateinit var preferencesDataStore: PreferencesDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOnboardShown()
    }

    private fun isOnboardShown() = lifecycleScope.launch {
        preferencesDataStore.read(DataStorePreferencesKey.SHOW_ONBOARD).collect {
            if (it != true) startActivity(OnboardActivity::class.java)
            else viewModel?.changeState()
        }
    }

    override fun stateChange(state: BaseVS?) {
        when (state) {
            MainActivityVS.StartState -> {
                Toast.makeText(this, "Text is changed.", Toast.LENGTH_LONG).show()
            }
        }
    }

}