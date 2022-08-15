package com.dag.mvvmarchitectureexample.ui.information

import android.os.Bundle
import android.view.View
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.databinding.ActivityInformationBinding
import com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import java.util.prefs.Preferences
import javax.inject.Inject

class InformationActivity:BaseActivity<InformationVM,ActivityInformationBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_information

    override fun getLayoutViewModel(): InformationVM = informationVM

    @Inject
    lateinit var informationVM: InformationVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.informationTV?.setOnClickListener(buttonClickListener)
    }

    private val buttonClickListener = View.OnClickListener {
        viewModel?.writeToPreferencesDatastore(PreferencesDataStore.INFORMATION_SCREEN,true)
    }
}