package com.smartpantry.manager.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.smartpantry.manager.R;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_settings);
        SharedPreferences p = getSharedPreferences("settings", MODE_PRIVATE);
        Switch alerts = findViewById(R.id.switchAlerts);
        alerts.setChecked(p.getBoolean("alerts", true));
        alerts.setOnCheckedChangeListener((button, checked) -> p.edit().putBoolean("alerts", checked).apply());
    }
}
