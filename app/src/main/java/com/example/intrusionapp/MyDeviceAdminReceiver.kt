package com.example.securityapp

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.os.UserHandle
import android.util.Log
import androidx.lifecycle.LifecycleOwner

class MyDeviceAdminReceiver : DeviceAdminReceiver() {
    override fun onPasswordFailed(context: Context, intent: Intent, userHandle: UserHandle) {
        super.onPasswordFailed(context, intent, userHandle)
        Log.d("MyDeviceAdminReceiver", "Password attempt failed")

        // Déclencher la capture de photo
        val photoCaptureManager = PhotoCaptureManager(context, context as LifecycleOwner)
        photoCaptureManager.capturePhoto()
    }
}
