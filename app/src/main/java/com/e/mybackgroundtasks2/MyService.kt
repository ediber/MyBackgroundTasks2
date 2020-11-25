package com.e.mybackgroundtasks2

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import timber.log.Timber

class MyService: Service() {

    private val CHANNEL_ID = "exampleServiceChannel"
    private lateinit var runnable: MyRunnable

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")

        return null
    }

    override fun onCreate() {
        super.onCreate()
        Timber.i("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Timber.i("onStart")

        val value = intent?.extras?.get("KEY_COMMAND")

        value?.let {
            if (value.equals("start")){
                showNotification()
                runnable = MyRunnable("service is running")
                Thread(runnable).start()
            } else{
                runnable?.toRun = false
                stopSelf()
            }
        }

        return START_NOT_STICKY
    }

    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        var notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_delete)
            .setContentTitle("textTitle")
            .setContentText("textContent")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "channel name", importance).apply {
                description = "descriptionText"
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            startForeground(1, notification)
        }
    }
}