package com.example.securityapp

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log

class UnlockMonitoringService : Service() {

    private lateinit var devicePolicyManager: MyDeviceAdminReceiver

    override fun onCreate() {
        super.onCreate()
        devicePolicyManager = MyDeviceAdminReceiver()

        // Enregistrer le receiver pour surveiller les tentatives de déverrouillage échouées
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_USER_PRESENT)
        registerReceiver(devicePolicyManager, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(devicePolicyManager)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
