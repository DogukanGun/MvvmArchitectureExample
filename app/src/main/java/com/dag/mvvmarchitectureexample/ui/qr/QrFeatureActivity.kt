package com.dag.mvvmarchitectureexample.ui.qr


import android.os.Bundle
import android.view.SurfaceHolder
import android.view.ViewTreeObserver
import android.widget.Toast
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.databinding.ActivityQrBinding
import javax.inject.Inject

class QrFeatureActivity:BaseActivity<QrVM,ActivityQrBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_qr

    override fun getLayoutViewModel(): QrVM = qrVM

    @Inject
    lateinit var qrVM: QrVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        barcodeHelper = QrHelper(this, this,this)
        createSurface()
        observeSurface()
    }

    private fun createSurface() {
        binding?.apply {
            squareViewQr.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceChanged(
                    holder: SurfaceHolder,
                    format: Int,
                    width: Int,
                    height: Int
                ) {
                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {}
                override fun surfaceCreated(holder: SurfaceHolder) {
                        startBarcodeDetection()
                }
            })
        }
    }
    fun startBarcodeDetection() {
        barcodeHelper.apply {
            binding?.squareViewQr?.let { initCameraSource(it) }
            startBarcodeDetection()
        }
    }
    private fun observeSurface() {
        binding?.apply {
            imageViewSquareView.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    imageViewSquareView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }
    override fun onBarcodeDetected(displayValue: String) {
        super.onBarcodeDetected(displayValue)
        Toast.makeText(this,displayValue,Toast.LENGTH_LONG).show()
    }
}