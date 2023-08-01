package com.ics342.labs

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat


class NotificationService : Service() {
    private val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(this)

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // If permission has not been granted, stop the service and return from
        // onStartCommand
        Log.d("NotificationService", "onStartCommand called")

        if (ContextCompat.checkSelfPermission(
                this@NotificationService,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("NotificationService", "Permission not granted. Stopping the service.")
            stopSelf()
            return START_NOT_STICKY
        }

        // Build notification

        // Create an explicit intent for an Activity in your app
        val notificationIntent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.star)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(NOTIFICATION_ID, builder.build())

        // Start the service as a foreground service
        startForeground(NOTIFICATION_ID, builder.build())

        return START_STICKY_COMPATIBILITY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // No need to implement for lab 8
        return null
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Lab7_channel"
            val descriptionText = "Lab7 channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Check if the channel already exists before creating it
            val notificationManager: NotificationManagerCompat =
                NotificationManagerCompat.from(context)
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(channel)
                Log.d("NotificationService", "Notification channel created.")
            } else {
                Log.d("NotificationService", "Notification channel already exists.")
            }
        }
    }

    companion object {
        private const val CHANNEL_ID = "LAB_7_CHANNEL_ID"
        private const val NOTIFICATION_ID = 1234
    }
}