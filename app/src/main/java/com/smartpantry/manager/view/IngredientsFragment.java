package com.smartpantry.manager.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.smartpantry.manager.R;
import com.smartpantry.manager.controller.PantryRepository;
import com.smartpantry.manager.controller.RecipeRepository;
import com.smartpantry.manager.model.PantryItem;
import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.model.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IngredientsFragment extends Fragment {
    private PantryAdapter adapter;
    private TextView count, empty, tvMatchSubtitle;
    private EditText etSearch;
    private CardView cardMatchAlert;
    private Button btnGoToRecipeAlert;
    private final List<PantryItem> pantry = new ArrayList<>();
    private final List<Recipe> recipes = new ArrayList<>();
    private final PantryRepository repository = new PantryRepository();
    private final RecipeRepository recipeRepository = new RecipeRepository();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ingredients, container, false);
        count = v.findViewById(R.id.tvCount);
        empty = v.findViewById(R.id.tvEmpty);
        etSearch = v.findViewById(R.id.etSearch);
        cardMatchAlert = v.findViewById(R.id.cardMatchAlert);
        tvMatchSubtitle = v.findViewById(R.id.tvMatchSubtitle);
        btnGoToRecipeAlert = v.findViewById(R.id.btnGoToRecipeAlert);
        
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { updateList(); }
        });

        RecyclerView rv = v.findViewById(R.id.rvPantry);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PantryAdapter(new PantryAdapter.Listener() {
            public void edit(PantryItem x) { openEditor(x); }
            public void update(PantryItem x) { openEditor(x); }
            public void delete(PantryItem x) { repository.delete(x.getId()); }
        });
        rv.setAdapter(adapter);

        v.findViewById(R.id.btnAdd).setOnClickListener(view -> 
            startActivity(new Intent(getActivity(), AddEditIngredientActivity.class)));

        repository.listen((snap, e) -> {
            pantry.clear();
            if (snap != null) for (QueryDocumentSnapshot d : snap) {
                PantryItem x = d.toObject(PantryItem.class);
                if (x != null) {
                    if (x.getId() == null || x.getId().isEmpty()) {
                        x.setId(d.getId());
                    }
                    pantry.add(x);
                }
            }
            updateList();
            checkMatchAlert();
        });

        recipeRepository.listen((snap, e) -> {
            recipes.clear();
            if (snap != null) for (QueryDocumentSnapshot d : snap) {
                Recipe r = d.toObject(Recipe.class);
                if (r != null) {
                    if (r.getId() == null || r.getId().isEmpty()) {
                        r.setId(d.getId());
                    }
                    recipes.add(r);
                }
            }
            checkMatchAlert();
        });

        return v;
    }

    private void checkMatchAlert() {
        Recipe matched = null;
        for (Recipe r : recipes) {
            if (RecipeMatcher.matches(r, pantry)) {
                matched = r;
                break;
            }
        }

        if (matched != null) {
            final Recipe finalRecipe = matched;
            cardMatchAlert.setVisibility(View.VISIBLE);
            tvMatchSubtitle.setText("You have all ingredients to make " + finalRecipe.getName() + "!");
            btnGoToRecipeAlert.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), RecipeDetailActivity.class);
                i.putExtra("recipeId", finalRecipe.getId());
                startActivity(i);
            });
        } else {
            cardMatchAlert.setVisibility(View.GONE);
        }
    }

    private void openEditor(PantryItem x) {
        Intent i = new Intent(getActivity(), AddEditIngredientActivity.class);
        i.putExtra("id", x.getId());
        i.putExtra("name", x.getName());
        i.putExtra("expiry", x.getExpiryDate());
        startActivity(i);
    }

    private void updateList() {
        String query = etSearch != null ? etSearch.getText().toString().trim().toLowerCase(Locale.ROOT) : "";
        List<PantryItem> filtered = new ArrayList<>();
        for (PantryItem item : pantry) {
            if (item.getName() != null && item.getName().toLowerCase(Locale.ROOT).contains(query)) {
                filtered.add(item);
            }
        }
        adapter.setItems(filtered);
        count.setText(filtered.size() + " item" + (filtered.size() == 1 ? "" : "s"));
        empty.setVisibility(filtered.isEmpty() ? View.VISIBLE : View.GONE);
        if (pantry.isEmpty()) {
            empty.setText("Your pantry is empty. Add ingredients to get started.");
        } else {
            empty.setText("No ingredients match your search.");
        }
    }
}
