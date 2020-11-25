package com.e.mybackgroundtasks2

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var alarmMgr: AlarmManager? = null
    private val requestIdPendingIntent = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_alarm.setOnClickListener(View.OnClickListener {

            val notificationIntent = Intent(this, MainActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(
                    this,
                    requestIdPendingIntent,
                    notificationIntent,
                    PendingIntent.FLAG_NO_CREATE
            )

            alarmMgr =
                    getSystemService(Context.ALARM_SERVICE) as? AlarmManager

            alarmMgr?.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + /*AlarmManager.INTERVAL_FIFTEEN_MINUTES*/ 5000,
                    AlarmManager.INTERVAL_HALF_HOUR,
                    pendingIntent
            )
        })
    }
}