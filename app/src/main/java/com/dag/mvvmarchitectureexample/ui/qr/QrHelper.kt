package com.dag.mvvmarchitectureexample.ui.qr

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.text.BoringLayout
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.roundToInt

class QrHelper(
    private val context: Context,
    private val activity: Activity,
    private val helperCallback: BarcodeHelperCallback,
    private val barcodeType: Int = Barcode.QR_CODE
) {
    private val barcodeDetector =
        BarcodeDetector.Builder(context).setBarcodeFormats(barcodeType).build()
    private val codeScanned = AtomicBoolean(false)
    private var cameraSource: CameraSource? = null
    private var surfaceHolder: SurfaceHolder? = null
    private var requestPermissionActivityResultLauncher: ActivityResultLauncher<String> =
        (activity as ComponentActivity).registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            surfaceHolder?.let { startCameraSource(it) }
        }

    fun initCameraSource(containerView: View) {
        context.let {
            val width = containerView.width
            val ratio = DeviceUtil.getScreenHeight(activity).toDouble() / DeviceUtil.getScreenWidth(
                activity
            ).toDouble()
            cameraSource = CameraSource.Builder(it, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(width, (width * ratio).roundToInt())
                .build()

            startCameraSource((containerView as SurfaceView).holder)
        }
    }

    private fun startCameraSource(surfaceHolder: SurfaceHolder) {
        context.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                try {
                    cameraSource?.start(surfaceHolder)
                } catch (e: Exception) {
                    helperCallback.onError(e.message.toString())
                }
            } else {
                this.surfaceHolder = surfaceHolder
                requestPermissionActivityResultLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    fun startBarcodeDetection() {
        codeScanned.set(false)
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {

            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                detections?.detectedItems?.let {
                    if (it.size() > 0 && !codeScanned.getAndSet(true)) {
                        val displayValue = it.valueAt(0).displayValue
                        helperCallback.onBarcodeDetected(displayValue)
                    }
                }
            }
        })
    }

    fun releaseBarcodeDetector() {
        codeScanned.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                cameraSource?.release()
                barcodeDetector.release()
            } catch (e: Exception) {
            }
        }
    }

    interface BarcodeHelperCallback {
        fun onBarcodeDetected(displayValue: String)
        fun onError(message: String)
    }

    data class BarcodeSettings(
        var parseResult:Boolean = true,
        var throwException:Boolean = false,
        var autoFocus:Boolean = true,
        var cameraSource:CameraSource? = null,
        var process: Detector.Processor<Barcode>,
    )
}