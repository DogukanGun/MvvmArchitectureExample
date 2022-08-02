package com.dag.mvvmarchitectureexample.ui.onboard

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.databinding.ActivityOnboardBinding
import com.dag.mvvmarchitectureexample.datastore.preferences.DataStorePreferencesKey
import com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import com.dag.mvvmarchitectureexample.ui.home.MainActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnboardActivity : BaseActivity<OnboardVM, ActivityOnboardBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_onboard

    override fun getLayoutViewModel(): OnboardVM = onboardVM

    @Inject
    lateinit var onboardVM: OnboardVM

    @Inject
    lateinit var preferencesDataStore: PreferencesDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.goMainScreenBTN?.setOnClickListener(goToMainScreenBTNListener)
    }

    private val goToMainScreenBTNListener = View.OnClickListener {
        writeResult()
        finish()
    }

    private fun writeResult() = lifecycleScope.launch{
        preferencesDataStore.write(DataStorePreferencesKey.SHOW_ONBOARD,true)
    }
}