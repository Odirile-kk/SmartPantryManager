package com.smartpantry.manager.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.smartpantry.manager.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_landing);
        new Handler().postDelayed(() -> findViewById(R.id.startButton).setVisibility(android.view.View.VISIBLE), 3000);
        findViewById(R.id.startButton).setVisibility(android.view.View.INVISIBLE);
        findViewById(R.id.startButton).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }
}
