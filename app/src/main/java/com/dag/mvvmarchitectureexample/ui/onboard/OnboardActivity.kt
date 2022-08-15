package com.dag.mvvmarchitectureexample.ui.onboard

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.data.Onboard
import com.dag.mvvmarchitectureexample.databinding.ActivityOnboardBinding
import com.dag.mvvmarchitectureexample.datastore.proto.ProtoDataStoreImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnboardActivity : BaseActivity<OnboardVM, ActivityOnboardBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_onboard

    override fun getLayoutViewModel(): OnboardVM = onboardVM

    @Inject
    lateinit var onboardVM: OnboardVM



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.goMainScreenBTN?.setOnClickListener(goToMainScreenBTNListener)
        viewModel?.writeOnboardType()
        viewModel?.onBoardIsShown()
    }

    private val goToMainScreenBTNListener = View.OnClickListener {
        viewModel?.writeOnboardType(Onboard.OnboardType.SHOWN)
        finish()
    }


}