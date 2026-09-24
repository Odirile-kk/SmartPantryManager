package com.smartpantry.manager.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smartpantry.manager.R;
import com.smartpantry.manager.controller.RecipeRepository;
import com.smartpantry.manager.controller.RecipeSeeder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        
        // Load initial fragment
        loadFragment(new IngredientsFragment());

        nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_pantry) {
                loadFragment(new IngredientsFragment());
                return true;
            }
            if (item.getItemId() == R.id.nav_recipes) {
                loadFragment(new SuggestionsFragment());
                return true;
            }
            if (item.getItemId() == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            return true;
        });

        new RecipeRepository().seedIfNeeded(RecipeSeeder.all(), () -> {
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
