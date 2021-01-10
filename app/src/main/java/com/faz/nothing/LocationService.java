package com.faz.nothing;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class LocationService extends Service {

    public static final Set<Location> locationSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
    public static final Set<LocationSetUpdateListener> locationUpdateListeners = new HashSet<>();

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final LocationProvider locationProvider = new LocationProvider();
    private final NetworkManager networkManager = new NetworkManager();

    private volatile boolean isRunning = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.log("Service started");

        new Thread(() -> {
            while (isRunning) {
                Location currentLocation = locationProvider.getCurrentLocation();
                Set<Location> locationsAround = networkManager.requestLocationsAround(currentLocation);
                locationSet.addAll(locationsAround);
                notifyLocationSetChanged();
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    // ignored
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Logger.log("Service stopped");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void notifyLocationSetChanged() {
        handler.post(() -> {
            for (LocationSetUpdateListener listener : locationUpdateListeners) {
                listener.onLocationSetChanged();
            }
        });
    }

    public interface LocationSetUpdateListener {

        void onLocationSetChanged();
    }
}