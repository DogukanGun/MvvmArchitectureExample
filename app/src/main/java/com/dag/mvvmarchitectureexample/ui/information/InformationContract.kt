package com.dag.mvvmarchitectureexample.ui.information

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.dag.mvvmarchitectureexample.base.IntentKey

class InformationContract:ActivityResultContract<Intent,Int>(){
    override fun createIntent(context: Context, input: Intent): Intent = input

    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        if (resultCode == Activity.RESULT_OK){
            intent?.getIntExtra(IntentKey.CalculationActivityNumber.name,0)?.let {
                return it
            }
        }
        return 0
    }
}