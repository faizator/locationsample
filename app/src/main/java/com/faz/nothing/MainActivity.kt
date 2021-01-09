package com.faz.nothing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity(), LocationService.LocationSetUpdateListener {

    private val startBtn: Button by lazy { findViewById<Button>(R.id.start) }
    private val stopBtn: Button by lazy { findViewById<Button>(R.id.stop) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceIntent = Intent(this, LocationService::class.java)
        startBtn.setOnClickListener {
            startService(serviceIntent)
        }
        stopBtn.setOnClickListener {
            stopService(serviceIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        drawLocations()
        LocationService.locationUpdateListeners.add(this)
    }

    override fun onPause() {
        super.onPause()
        LocationService.locationUpdateListeners.remove(this)
    }

    override fun onLocationSetChanged() {
        drawLocations()
    }

    private fun drawLocations() {
        val locations = LocationService.locationSet
        Logger.log("Drawing $locations")
    }
}