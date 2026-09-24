package com.smartpantry.manager.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartpantry.manager.R;
import com.smartpantry.manager.controller.PantryRepository;
import com.smartpantry.manager.model.PantryItem;

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

        new PantryRepository().save(new PantryItem(id, n, expiry.getText().toString().trim()));
        Toast.makeText(this, id == null ? "Ingredient added" : "Ingredient updated", Toast.LENGTH_SHORT).show();
        finish();
    }
}
