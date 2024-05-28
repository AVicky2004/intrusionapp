package com.example.securityapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Lancer le service pour enregistrer le récepteur de diffusion du raté du code PIN
            val serviceIntent = Intent(context, PinFailureService::class.java)
            context.startService(serviceIntent)
        }
    }
}
