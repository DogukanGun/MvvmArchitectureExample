package com.dag.mvvmarchitectureexample.ui.information

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.adapter.ItemClickListener
import com.dag.mvvmarchitectureexample.adapter.basicAdapter
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.databinding.ActivityInformationBinding
import com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import java.util.*
import javax.inject.Inject


class InformationActivity:BaseActivity<InformationVM,ActivityInformationBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_information

    override fun getLayoutViewModel(): InformationVM = informationVM

    @Inject
    lateinit var informationVM: InformationVM

    private val informationRepository = InformationRepository()

    private val videoSaveUri by lazy { Uri.fromFile(externalCacheDir?.resolve("video.mp4")) }

    private lateinit var captureVideoActivityResultLauncher:ActivityResultLauncher<Uri>
    private lateinit var pickContactActivityResultLauncher:ActivityResultLauncher<Intent>
    private lateinit var openDocumentActivityResultLauncher:ActivityResultLauncher<Intent>
    private lateinit var requestPermissionActivityResultLauncher:ActivityResultLauncher<String>
    private lateinit var requestMultiplePermissionActivityResultLauncher:ActivityResultLauncher<Array<String>>
    private lateinit var takePicturePreviewActivityResultLauncher:ActivityResultLauncher<Intent>
    private lateinit var watchFaceEditorContractActivityResultLauncher:ActivityResultLauncher<Intent>
    private lateinit var takePictureActivityResultLauncher:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.apply{
            informationBT.setOnClickListener(buttonClickListener)
            permissionListRV.layoutManager = LinearLayoutManager(this@InformationActivity)
            permissionListRV.adapter = basicAdapter<InformationItem> {
                itemLayoutId = R.layout.permission_layout
                itemClickListener = recyclerItemClickListener
                list = informationRepository.getInformationItemList()
            }
        }
        registerAllResultLaunchers()
    }

    private fun registerAllResultLaunchers(){
        captureVideoActivityResultLauncher = registerForActivityResult(ActivityResultContracts.CaptureVideo(),
            {
                Toast.makeText(this,"Kaydedildi : $it",Toast.LENGTH_SHORT).show()
            }
        )
        requestMultiplePermissionActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

        }
    }

    private val recyclerItemClickListener = ItemClickListener<InformationItem>{position, item ->
        executeCommand(item.informationText)
    }

    private val buttonClickListener = View.OnClickListener {
        viewModel?.writeToPreferencesDatastore(PreferencesDataStore.INFORMATION_SCREEN,true)
    }

    fun executeCommand(command:String){
        Toast.makeText(this,command,Toast.LENGTH_SHORT).show()
        when(command){
            InformationTypes.CaptureVideo.name ->{
                val imageUri = FileProvider.getUriForFile(
                    this,
                    "com.dag.mvvmarchitectureexample.provider",
                    Environment.getExternalStoragePublicDirectory(Environment.
                    DIRECTORY_DCIM+"/Camera/video.mp4")
                )
                captureVideoActivityResultLauncher.launch(imageUri)
            }
            InformationTypes.PickContact.name ->{

            }
            InformationTypes.OpenDocument.name ->{

            }
            InformationTypes.RequestPermission.name ->{
                requestPermissionActivityResultLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
            InformationTypes.RequestMultiplePermission.name ->{
                requestMultiplePermissionActivityResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA
                    )
                )
            }
            InformationTypes.TakePicturePreview.name ->{

            }
            InformationTypes.WatchFaceEditorContract.name ->{

            }
            InformationTypes.TakePicture.name ->{

            }
        }
    }
}