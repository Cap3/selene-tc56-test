package de.cap3.selenetc56test

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import java.util.*
import kotlin.concurrent.schedule

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    val seleneService = EmdkConfigurator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        buttonStart.setOnClickListener({ _ -> seleneService.startEmdkConfiguration() })

        delayedIntent.setOnClickListener({_ -> sendDelayedEmergencyIntent()})
    }

    fun sendDelayedEmergencyIntent() {

        val emergencyIntent = Intent("de.cap3.selenetc56test.intent.action.EMERGENCY")
        emergencyIntent.addCategory("de.cap3.selenetc56test")

        Timer().schedule(10000) {
            sendBroadcast(emergencyIntent)
        }
    }
}
