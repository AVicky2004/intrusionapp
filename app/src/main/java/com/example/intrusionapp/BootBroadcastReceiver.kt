package com.example.securityapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootBroadcastReceiver", "Device booted, starting service...")

            // Lancer le service pour surveiller les tentatives de déverrouillage échouées
            val serviceIntent = Intent(context, UnlockMonitoringService::class.java)
            context.startService(serviceIntent)
        }
    }
}
