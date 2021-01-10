package com.faz.nothing

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

class LocationService : Service() {

    private val handler = Handler(Looper.getMainLooper())

    private val locationProvider = LocationProvider()
    private val networkManager = NetworkManager()

    @Volatile
    private var isRunning = true

    override fun onCreate() {
        super.onCreate()
        Logger.log("Service started")
        thread {
            while (isRunning) {
                val currentLocation = locationProvider.getCurrentLocation()
                val locationsAround = networkManager.requestLocationsAround(currentLocation)
                locationSet.addAll(locationsAround)
                notifyLocationSetChanged()
                Thread.sleep(5000)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        Logger.log("Service stopped")
    }

    override fun onBind(intent: Intent): IBinder? = null

    private fun notifyLocationSetChanged() {
        handler.post {
            locationUpdateListeners.forEach {
                it.onLocationSetChanged()
            }
        }
    }

    interface LocationSetUpdateListener {

        fun onLocationSetChanged()
    }

    companion object {

        val locationSet = Collections.newSetFromMap(ConcurrentHashMap<Location, Boolean>())

        val locationUpdateListeners: MutableSet<LocationSetUpdateListener> = mutableSetOf()
    }
}