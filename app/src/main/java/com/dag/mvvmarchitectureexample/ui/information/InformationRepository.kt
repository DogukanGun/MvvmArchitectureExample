package com.dag.mvvmarchitectureexample.ui.information

class InformationRepository {

    fun getInformationItemList(): List<InformationItem> =
        listOf(
            InformationItem(InformationTypes.CaptureVideo.name),
            InformationItem(InformationTypes.PickContact.name),
            InformationItem(InformationTypes.OpenDocument.name),
            InformationItem(InformationTypes.RequestPermission.name),
            InformationItem(InformationTypes.RequestMultiplePermission.name),
            InformationItem(InformationTypes.TakePicturePreview.name),
            InformationItem(InformationTypes.WatchFaceEditorContract.name),
            InformationItem(InformationTypes.TakePicture.name),
            )
}