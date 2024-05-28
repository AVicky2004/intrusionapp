package com.example.intrusionapp

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.example.securityapp.PhotoCaptureManager

class MyAccessibilityService : AccessibilityService() {

    private lateinit var photoCaptureManager: PhotoCaptureManager

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val className = event.className?.toString()
            if (className == "com.android.systemui.keyguard.KeyguardSecurityContainer") {
                // Code PIN ou pattern saisi
                // Vous pouvez ajouter ici une logique pour détecter l'échec,
                // par exemple via des événements spécifiques ou des états de l'UI
                // Ensuite, déclencher la capture de la photo
                photoCaptureManager.capturePhoto();
            }
        }
    }
    override fun onInterrupt() {}

}