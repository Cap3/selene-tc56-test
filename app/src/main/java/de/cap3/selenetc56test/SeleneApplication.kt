package de.cap3.selenetc56test

import android.app.Application
import android.content.Context
import android.os.PowerManager



class SeleneApplication : Application() {

    var wakeLock: PowerManager.WakeLock? = null

    override fun onCreate() {
        super.onCreate()

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager

        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag")
        wakeLock?.acquire()
    }

    override fun onTerminate() {
        wakeLock?.release()
        super.onTerminate()
    }
}