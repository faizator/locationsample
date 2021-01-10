package com.faz.nothing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public final class MainActivity extends AppCompatActivity implements LocationService.LocationSetUpdateListener {

    private Button startBtn;
    private Button stopBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.start);
        stopBtn = findViewById(R.id.stop);

        Intent serviceIntent = new Intent(this, LocationService.class);
        startBtn.setOnClickListener(v -> startService(serviceIntent));
        stopBtn.setOnClickListener(v -> stopService(serviceIntent));
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawLocations();
        LocationService.locationUpdateListeners.add(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationService.locationUpdateListeners.remove(this);
    }

    @Override
    public void onLocationSetChanged() {
        drawLocations();
    }

    private void drawLocations() {
        Set<Location> locations = LocationService.locationSet;
        Logger.log("Drawing " + locations);
    }
}
