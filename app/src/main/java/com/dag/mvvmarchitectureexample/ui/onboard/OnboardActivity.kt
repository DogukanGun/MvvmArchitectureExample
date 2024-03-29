package com.dag.mvvmarchitectureexample.ui.onboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.base.IntentKey
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
        val activityResult = intent.getIntExtra(IntentKey.OnboardActivityResult.name,Activity.RESULT_OK)
        setResult(activityResult,intent)
        finish()
    }

    companion object{
        fun newIntent(context: Context,finishResult:Int):Intent {
            val intent = Intent(context,OnboardActivity::class.java)
            intent.putExtra(IntentKey.OnboardActivityResult.name,finishResult)
            return intent
        }
    }

}