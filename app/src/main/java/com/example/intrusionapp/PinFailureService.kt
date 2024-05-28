package com.example.securityapp

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class PinFailureService : Service() {
    private val pinFailureReceiver = PinFailureReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Enregistrer le récepteur de diffusion du raté du code PIN
        val filter = IntentFilter()
        filter.addAction("android.intent.action.USER_PRESENT")
        registerReceiver(pinFailureReceiver, filter)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Désenregistrer le récepteur de diffusion du raté du code PIN
        unregisterReceiver(pinFailureReceiver)
    }
}
