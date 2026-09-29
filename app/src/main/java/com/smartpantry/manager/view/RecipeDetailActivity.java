package com.smartpantry.manager.view;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.smartpantry.manager.R;
import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.model.RecipeIngredient;

import java.util.List;
import java.util.Map;

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

            TextView tvTitle = findViewById(R.id.tvRecipeName);
            TextView tvIng = findViewById(R.id.tvIngredients);
            TextView tvSteps = findViewById(R.id.tvSteps);

            Recipe r = d.toObject(Recipe.class);
            if (r != null && r.getName() != null) {
                tvTitle.setText(r.getName());
            } else if (d.getString("name") != null) {
                tvTitle.setText(d.getString("name"));
            }

            // Extract Ingredients (handles RecipeIngredient list, String list, or Map list)
            StringBuilder ingBuilder = new StringBuilder();
            if (r != null && r.getIngredients() != null && !r.getIngredients().isEmpty()) {
                for (RecipeIngredient x : r.getIngredients()) {
                    if (x != null && x.getName() != null && !x.getName().trim().isEmpty()) {
                        ingBuilder.append("• ").append(capitalize(x.getName())).append("\n");
                    }
                }
            }

            if (ingBuilder.length() == 0) {
                Object rawIng = d.get("ingredients");
                if (rawIng instanceof List) {
                    for (Object obj : (List<?>) rawIng) {
                        if (obj instanceof String && !((String) obj).trim().isEmpty()) {
                            ingBuilder.append("• ").append(capitalize((String) obj)).append("\n");
                        } else if (obj instanceof Map) {
                            Object nameObj = ((Map<?, ?>) obj).get("name");
                            if (nameObj != null && !nameObj.toString().trim().isEmpty()) {
                                ingBuilder.append("• ").append(capitalize(nameObj.toString())).append("\n");
                            }
                        }
                    }
                }
            }

            if (ingBuilder.length() > 0) {
                tvIng.setText(ingBuilder.toString().trim());
            } else {
                tvIng.setText("No ingredients listed.");
            }

            // Extract Steps (handles String list or Map list)
            StringBuilder stepsBuilder = new StringBuilder();
            if (r != null && r.getSteps() != null && !r.getSteps().isEmpty()) {
                int stepNum = 1;
                for (String s : r.getSteps()) {
                    if (s != null && !s.trim().isEmpty()) {
                        stepsBuilder.append(stepNum++).append(". ").append(s.trim()).append("\n\n");
                    }
                }
            }

            if (stepsBuilder.length() == 0) {
                Object rawSteps = d.get("steps");
                if (rawSteps instanceof List) {
                    int stepNum = 1;
                    for (Object obj : (List<?>) rawSteps) {
                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            stepsBuilder.append(stepNum++).append(". ").append(obj.toString().trim()).append("\n\n");
                        }
                    }
                }
            }

            if (stepsBuilder.length() > 0) {
                tvSteps.setText(stepsBuilder.toString().trim());
            } else {
                tvSteps.setText("No instructions provided.");
            }

        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error loading recipe", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private String capitalize(String str) {
        if (str == null || str.trim().isEmpty()) return "";
        String trimmed = str.trim();
        return Character.toUpperCase(trimmed.charAt(0)) + trimmed.substring(1);
    }
}
