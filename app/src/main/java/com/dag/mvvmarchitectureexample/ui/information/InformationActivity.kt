package com.dag.mvvmarchitectureexample.ui.information

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dag.mvvmarchitectureexample.R
import com.dag.mvvmarchitectureexample.adapter.ItemClickListener
import com.dag.mvvmarchitectureexample.adapter.basicAdapter
import com.dag.mvvmarchitectureexample.base.BaseActivity
import com.dag.mvvmarchitectureexample.databinding.ActivityInformationBinding
import com.dag.mvvmarchitectureexample.datastore.preferences.PreferencesDataStore
import com.dag.mvvmarchitectureexample.ui.calculation.CalculationActivity
import com.dag.mvvmarchitectureexample.ui.onboard.OnboardActivity
import javax.inject.Inject


class InformationActivity:BaseActivity<InformationVM,ActivityInformationBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_information

    override fun getLayoutViewModel(): InformationVM = informationVM

    @Inject
    lateinit var informationVM: InformationVM

    private val imagePath = "image/*"

    private lateinit var pageChangerActivityResultLauncher:ActivityResultLauncher<Intent>
    private lateinit var captureVideoActivityResultLauncher:ActivityResultLauncher<Uri>
    private lateinit var pickContactActivityResultLauncher:ActivityResultLauncher<Void?>
    private lateinit var openDocumentActivityResultLauncher:ActivityResultLauncher<Array<String>>
    private lateinit var requestPermissionActivityResultLauncher:ActivityResultLauncher<String>
    private lateinit var requestMultiplePermissionActivityResultLauncher:ActivityResultLauncher<Array<String>>
    private lateinit var takePicturePreviewActivityResultLauncher:ActivityResultLauncher<Void?>
    //private lateinit var watchFaceEditorContractActivityResultLauncher:ActivityResultLauncher<EditorRequest>
    private lateinit var takePictureActivityResultLauncher:ActivityResultLauncher<Uri>
    private lateinit var ownActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.apply{
            informationBT.setOnClickListener(buttonClickListener)
            permissionListRV.layoutManager = LinearLayoutManager(this@InformationActivity)
            permissionListRV.adapter = basicAdapter<InformationItem> {
                itemLayoutId = R.layout.permission_layout
                itemClickListener = recyclerItemClickListener
                list = informationVM.getInformationItemList()
            }
        }
        registerAllResultLaunchers()
    }

    private fun registerAllResultLaunchers(){
        pageChangerActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                startOnboardWithCustomAnim()
            }
        }
        captureVideoActivityResultLauncher = registerForActivityResult(ActivityResultContracts.CaptureVideo()
        ) {
            Toast.makeText(this, "Saved : $it", Toast.LENGTH_SHORT).show()
        }
        takePictureActivityResultLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){
            Toast.makeText(this, "Saved : $it", Toast.LENGTH_SHORT).show()
        }
        takePicturePreviewActivityResultLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            Toast.makeText(this, "Saved : $it", Toast.LENGTH_SHORT).show()
        }
        pickContactActivityResultLauncher = registerForActivityResult(ActivityResultContracts.PickContact()){
            Toast.makeText(this, "Saved : $it", Toast.LENGTH_SHORT).show()
        }
        openDocumentActivityResultLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()){
            Toast.makeText(this, "Saved : $it", Toast.LENGTH_SHORT).show()
        }
        requestMultiplePermissionActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            Toast.makeText(this, "Saved : $it", Toast.LENGTH_SHORT).show()
        }
        requestPermissionActivityResultLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            Toast.makeText(this, "Saved : $it", Toast.LENGTH_SHORT).show()
        }
        ownActivityResultLauncher = registerForActivityResult(InformationContract()){
            Toast.makeText(this,"Number : $it",Toast.LENGTH_LONG).show()
        }
        /*watchFaceEditorContractActivityResultLauncher = registerForActivityResult(
            WatchFaceEditorContract()
        ){

        }*/
    }

    private val recyclerItemClickListener = ItemClickListener<InformationItem>{position, item ->
        executeCommand(item.informationText)
    }

    private val buttonClickListener = View.OnClickListener {
        viewModel?.writeToPreferencesDatastore(PreferencesDataStore.INFORMATION_SCREEN,true)
        finish()
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
                binding?.root?.let {
                    pickContactActivityResultLauncher.launch(
                        null,
                        ActivityOptionsCompat.makeScaleUpAnimation(it,10,10,20,20)
                    )
                }
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
            InformationTypes.StartActivity.name ->{
                binding?.root?.let {
                    pageChangerActivityResultLauncher.launch(
                        OnboardActivity.newIntent(this,Activity.RESULT_OK),
                        ActivityOptionsCompat.makeScaleUpAnimation(it,10,10,20,20)
                    )
                }
            }
            InformationTypes.OwnIntent.name ->{
                ownActivityResultLauncher.launch(
                    CalculationActivity.newIntent(this)
                )
            }
        }
    }
    private fun startOnboardWithCustomAnim(){
        pageChangerActivityResultLauncher.launch(
            OnboardActivity.newIntent(this,Activity.RESULT_CANCELED),
            ActivityOptionsCompat.makeCustomAnimation(this,R.anim.slide_in_left,R.anim.slide_out_left)
        )
    }
}