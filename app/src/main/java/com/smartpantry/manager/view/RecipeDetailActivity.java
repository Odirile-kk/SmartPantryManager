package com.smartpantry.manager.view;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.smartpantry.manager.R;
import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.model.RecipeIngredient;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_recipe_detail);
        String id = getIntent().getStringExtra("recipeId");
        if (id == null || id.trim().isEmpty()) {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        FirebaseFirestore.getInstance().collection("recipes").document(id).get().addOnSuccessListener(d -> {
            if (!d.exists()) {
                Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            Recipe r = d.toObject(Recipe.class);
            if (r == null) return;
            if (r.getName() != null) {
                ((TextView) findViewById(R.id.tvRecipeName)).setText(r.getName());
            }
            StringBuilder ing = new StringBuilder();
            if (r.getIngredients() != null) {
                for (RecipeIngredient x : r.getIngredients()) {
                    if (x != null && x.getName() != null) {
                        ing.append("• ").append(x.getName()).append("\n");
                    }
                }
            }
            ((TextView) findViewById(R.id.tvIngredients)).setText(ing.toString());
            StringBuilder steps = new StringBuilder();
            int n = 1;
            if (r.getSteps() != null) {
                for (String s : r.getSteps()) {
                    if (s != null) {
                        steps.append(n++).append(". ").append(s).append("\n\n");
                    }
                }
            }
            ((TextView) findViewById(R.id.tvSteps)).setText(steps.toString());
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error loading recipe", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
