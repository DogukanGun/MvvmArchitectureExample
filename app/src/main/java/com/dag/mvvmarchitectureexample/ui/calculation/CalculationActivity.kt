package com.dag.mvvmarchitectureexample.ui.calculation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.base.IntentKey
import com.dag.mvvmarchitectureexample.databinding.ActivityCalculationBinding
import javax.inject.Inject

class CalculationActivity:BaseActivity<CalculationVM,ActivityCalculationBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_calculation

    override fun getLayoutViewModel(): CalculationVM = calculationVM

    private var resultNumber:Int = 0

    @Inject
    lateinit var calculationVM: CalculationVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.resultBTN?.setOnClickListener(resultBtnClickListener)
        binding?.calculationET?.addTextChangedListener(calculationEtTextWatcher)
    }

    private val calculationEtTextWatcher = object :TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            p0?.toString()?.toInt()?.let {
                resultNumber = it
            }
        }
    }

    private val resultBtnClickListener = View.OnClickListener {
        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra(IntentKey.CalculationActivityNumber.name,resultNumber)
            }
        )
        finish()
    }

    companion object{
        fun newIntent(context:Context):Intent = Intent(
            context,
            CalculationActivity::class.java
        )
    }
}