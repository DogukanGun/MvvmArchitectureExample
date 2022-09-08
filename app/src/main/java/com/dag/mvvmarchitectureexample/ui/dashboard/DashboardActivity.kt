package com.dag.mvvmarchitectureexample.ui.dashboard

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.adapter.ItemClickListener
import com.dag.mvvmarchitectureexample.adapter.basicAdapter
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.databinding.ActivityDashboardBinding
import com.dag.mvvmarchitectureexample.ui.information.InformationActivity
import com.dag.mvvmarchitectureexample.ui.qr.QrFeatureActivity
import com.dag.mvvmarchitectureexample.ui.sms.SmsActivity
import javax.inject.Inject

class DashboardActivity:BaseActivity<DashboardVM,ActivityDashboardBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_dashboard

    override fun getLayoutViewModel(): DashboardVM = dashboardVM

    @Inject
    lateinit var dashboardVM: DashboardVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.optionsRV?.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = basicAdapter<DashboardItem> {
                itemLayoutId = R.layout.feature_layout
                itemClickListener = recyclerListener
                list = dashboardVM.getDashboardItems()
            }
        }
    }

    private val recyclerListener = ItemClickListener<DashboardItem>{ pos,item ->
        executeCommand(item.text)
    }

    private fun executeCommand(command:String){
        when(command){
            Feature.ActivityResultLauncher.name ->{
                startActivity(InformationActivity::class.java)
            }
            Feature.Sms.name ->{
                startActivity(SmsActivity::class.java)
            }
            Feature.QrCode.name ->{
                startActivity(QrFeatureActivity::class.java)
            }
        }
    }


}