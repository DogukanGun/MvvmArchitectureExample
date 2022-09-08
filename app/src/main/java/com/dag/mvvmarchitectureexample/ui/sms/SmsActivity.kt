package com.dag.mvvmarchitectureexample.ui.sms

import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.databinding.ActivitySmsBinding
import javax.inject.Inject

class SmsActivity:BaseActivity<SmsVM,ActivitySmsBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_sms

    override fun getLayoutViewModel(): SmsVM = smsVM

    @Inject
    lateinit var smsVM: SmsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val smsHelper = SmsHelper(this)
        smsHelper.startSmsUserVerification("DAG",{
            Toast.makeText(this,"Sms was sent.",Toast.LENGTH_LONG).show()
        })
        startActivity(smsHelper.sendSms())
    }


}