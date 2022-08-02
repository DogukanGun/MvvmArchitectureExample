package com.dag.mvvmarchitectureexample.base

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dag.mvvmarchitectureexample.BR
import dagger.android.AndroidInjection

abstract class BaseActivity<VM : BaseVM, DB : ViewDataBinding> : AppCompatActivity() {

    abstract fun getLayoutId(): Int
    abstract fun getLayoutViewModel(): VM

    protected var binding: DB? = null
    protected var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = getLayoutViewModel()
        (viewModel as BaseVM).let { viewmodel ->
            if (!viewmodel.state.hasActiveObservers()) {
                viewmodel.state.observe(this) {
                    stateChange(it)
                }
            }
        }
    }

    fun startActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    open fun stateChange(state: BaseVS?) {

    }

}