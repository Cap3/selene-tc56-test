package de.cap3.selenetc56test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Vibrator
import android.util.Log
import android.view.KeyEvent

class ButtonIntentReceiver : BroadcastReceiver() {

    //endregion

    //region Lifecycle

    override fun onReceive(context: Context, intent: Intent) {
        val keyEvent = intent.extras?.get("android.intent.extra.KEY_EVENT") as KeyEvent?

        if(keyEvent != null) {
            val downTimestamp = keyEvent.downTime
            val upTimestamp: Long
            if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                upTimestamp = 0
            } else {
                upTimestamp = keyEvent.eventTime
            }

            Log.d(TAG, "EMDK received key event, down: $downTimestamp, up: $upTimestamp")
        }

        val vibratorService = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibratorService.vibrate(1000)
    }

    companion object {
        //region Fields

        private val TAG = ButtonIntentReceiver::class.java.name
    }

    //endregion
}