package com.smartpantry.manager.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.smartpantry.manager.R;
import com.smartpantry.manager.controller.PantryRepository;
import com.smartpantry.manager.model.PantryItem;
import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.model.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class AddEditIngredientActivity extends AppCompatActivity {
    private EditText name, expiry;
    private String id;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_add_edit);

        name = findViewById(R.id.etName);
        expiry = findViewById(R.id.etExpiry);

        id = getIntent().getStringExtra("id");
        if (id != null) {
            ((TextView) findViewById(R.id.tvTitle)).setText("Update ingredient");
            ((Button) findViewById(R.id.btnSave)).setText("Update");
            name.setText(getIntent().getStringExtra("name"));
            expiry.setText(getIntent().getStringExtra("expiry"));
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> save());
        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());
    }

    private void save() {
        String n = name.getText().toString().trim();
        if (TextUtils.isEmpty(n)) {
            name.setError("Name is required");
            return;
        }

        PantryItem item = new PantryItem(id, n, expiry.getText().toString().trim());
        new PantryRepository().save(item);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("pantry").get().addOnSuccessListener(pantrySnap -> {
            List<PantryItem> pantry = new ArrayList<>();
            if (pantrySnap != null) {
                for (QueryDocumentSnapshot doc : pantrySnap) {
                    PantryItem pi = doc.toObject(PantryItem.class);
                    pi.setId(doc.getId());
                    pantry.add(pi);
                }
            }

            db.collection("recipes").get().addOnSuccessListener(recipeSnap -> {
                Recipe matched = null;
                if (recipeSnap != null) {
                    for (QueryDocumentSnapshot doc : recipeSnap) {
                        Recipe r = doc.toObject(Recipe.class);
                        if (RecipeMatcher.matches(r, pantry)) {
                            matched = r;
                            break;
                        }
                    }
                }

                if (matched != null) {
                    final Recipe finalRecipe = matched;
                    new AlertDialog.Builder(AddEditIngredientActivity.this)
                            .setTitle("Match!")
                            .setMessage("You have all ingredients to make " + finalRecipe.getName() + "!")
                            .setPositiveButton("Go to Recipe", (dialog, which) -> {
                                Intent intent = new Intent(AddEditIngredientActivity.this, RecipeDetailActivity.class);
                                intent.putExtra("recipeId", finalRecipe.getId());
                                startActivity(intent);
                                finish();
                            })
                            .setNegativeButton("Close", (dialog, which) -> finish())
                            .setCancelable(false)
                            .show();
                } else {
                    Toast.makeText(AddEditIngredientActivity.this, id == null ? "Ingredient added" : "Ingredient updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(e -> finish());
        }).addOnFailureListener(e -> finish());
    }
}
