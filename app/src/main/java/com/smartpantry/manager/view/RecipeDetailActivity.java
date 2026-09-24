package com.smartpantry.manager.view;

import android.os.Bundle;
import android.widget.TextView;

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
        FirebaseFirestore.getInstance().collection("recipes").document(id).get().addOnSuccessListener(d -> {
            Recipe r = d.toObject(Recipe.class);
            if (r == null) return;
            ((TextView) findViewById(R.id.tvRecipeName)).setText(r.getName());
            StringBuilder ing = new StringBuilder();
            for (RecipeIngredient x : r.getIngredients())
                ing.append("• ").append(x.getName()).append("\n");
            ((TextView) findViewById(R.id.tvIngredients)).setText(ing.toString());
            StringBuilder steps = new StringBuilder();
            int n = 1;
            for (String s : r.getSteps()) steps.append(n++).append(". ").append(s).append("\n\n");
            ((TextView) findViewById(R.id.tvSteps)).setText(steps.toString());
        });
    }
}
