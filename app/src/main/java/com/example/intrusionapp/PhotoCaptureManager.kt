package com.example.securityapp

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

class PhotoCaptureManager(private val context: Context, private val lifecycleOwner: LifecycleOwner) {

    private lateinit var imageCapture: ImageCapture

    fun capturePhoto() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            imageCapture = ImageCapture.Builder().build()

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                imageCapture
            )

            val photoFile = File(context.externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")

            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        // Photo saved, send it via email
                        sendEmailWithAttachment(photoFile)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        // Handle error
                        exception.printStackTrace()
                    }
                }
            )
        }, ContextCompat.getMainExecutor(context))
    }

    private fun sendEmailWithAttachment(file: File) {
        val username = "your-email@gmail.com"
        val password = "your-password"

        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.port", "587")
        }

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })

        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(username))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse("recipient-email@gmail.com"))
                subject = "Intruder Alert"
                val mimeBodyPart = MimeBodyPart().apply {
                    setContent("Intruder detected. See the attached photo.", "text/html")
                }
                val attachmentBodyPart = MimeBodyPart().apply {
                    attachFile(file)
                }
                val multipart = MimeMultipart().apply {
                    addBodyPart(mimeBodyPart)
                    addBodyPart(attachmentBodyPart)
                }
                setContent(multipart)
            }
            Transport.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }
}
