package com.dag.mvvmarchitectureexample.ui.sms

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.phone.SmsRetriever


class SmsHelper(private val activity: Activity) {

    fun startSmsUserVerification(senderName: String?, onSuccess: () -> Unit) {
        if (activity is FragmentActivity) {
            if (activity != null) {

                SmsRetriever.getClient(activity ?: return).also {
                    it.startSmsUserConsent(senderName)
                    onSuccess.invoke()
                }
            }
        }
    }

    fun sendSms():Intent{
        /*val defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(activity)
        val shareBody = "Selam"
        val sendIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:5395944673"))
        sendIntent.putExtra("sms_body", shareBody)
        if (defaultSmsPackageName != null) {
            sendIntent.`package` = defaultSmsPackageName
        }*/
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.putExtra("sms_body", "default content")
        sendIntent.type = "vnd.android-dir/mms-sms"
        return sendIntent
    }

    fun getOtpReceiverIntent() = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)

    fun getExtraMessage() = SmsRetriever.EXTRA_SMS_MESSAGE
}