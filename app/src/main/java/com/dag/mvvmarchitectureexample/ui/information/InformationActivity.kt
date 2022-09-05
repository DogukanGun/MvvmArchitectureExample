package com.dag.mvvmarchitectureexample.ui.information

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.wear.watchface.editor.EditorRequest
import androidx.wear.watchface.editor.WatchFaceEditorContract
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

    private val imagePath = "image/*"

    private lateinit var captureVideoActivityResultLauncher:ActivityResultLauncher<Uri>
    private lateinit var pickContactActivityResultLauncher:ActivityResultLauncher<Void?>
    private lateinit var openDocumentActivityResultLauncher:ActivityResultLauncher<Array<String>>
    private lateinit var requestPermissionActivityResultLauncher:ActivityResultLauncher<String>
    private lateinit var requestMultiplePermissionActivityResultLauncher:ActivityResultLauncher<Array<String>>
    private lateinit var takePicturePreviewActivityResultLauncher:ActivityResultLauncher<Void?>
    private lateinit var watchFaceEditorContractActivityResultLauncher:ActivityResultLauncher<EditorRequest>
    private lateinit var takePictureActivityResultLauncher:ActivityResultLauncher<Uri>

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
        captureVideoActivityResultLauncher = registerForActivityResult(ActivityResultContracts.CaptureVideo()
        ) {
            Toast.makeText(this, "Kaydedildi : $it", Toast.LENGTH_SHORT).show()
        }
        takePictureActivityResultLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){
            Toast.makeText(this, "Kaydedildi : $it", Toast.LENGTH_SHORT).show()
        }
        takePicturePreviewActivityResultLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            Toast.makeText(this, "Kaydedildi : $it", Toast.LENGTH_SHORT).show()
        }
        pickContactActivityResultLauncher = registerForActivityResult(ActivityResultContracts.PickContact()){
            Toast.makeText(this, "Kaydedildi : $it", Toast.LENGTH_SHORT).show()
        }
        openDocumentActivityResultLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()){
            Toast.makeText(this, "Kaydedildi : $it", Toast.LENGTH_SHORT).show()
        }
        requestMultiplePermissionActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            Toast.makeText(this, "Kaydedildi : $it", Toast.LENGTH_SHORT).show()
        }
        requestPermissionActivityResultLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            Toast.makeText(this, "Kaydedildi : $it", Toast.LENGTH_SHORT).show()
        }
        watchFaceEditorContractActivityResultLauncher = registerForActivityResult(
            WatchFaceEditorContract()
        ){

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
                pickContactActivityResultLauncher.launch(null)
            }
            InformationTypes.OpenDocument.name ->{
                openDocumentActivityResultLauncher.launch(arrayOf(imagePath))
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
                takePicturePreviewActivityResultLauncher.launch(null)
            }
            InformationTypes.WatchFaceEditorContract.name ->{
                /*watchFaceEditorContractActivityResultLauncher.launch(
                    EditorRequest(
                        WILL COME LATER
                    )
                )*/
            }
            InformationTypes.TakePicture.name ->{
                val imageUri = FileProvider.getUriForFile(
                    this,
                    "com.dag.mvvmarchitectureexample.provider",
                    Environment.getExternalStoragePublicDirectory(Environment.
                    DIRECTORY_DCIM+"/Camera/image.png")
                )
                takePictureActivityResultLauncher.launch(imageUri)
            }
        }
    }
}