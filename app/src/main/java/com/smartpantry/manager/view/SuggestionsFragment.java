package com.smartpantry.manager.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.Collections;
import java.util.List;

public class SuggestionsFragment extends Fragment {
    private final List<PantryItem> pantry = new ArrayList<>();
    private final List<Recipe> recipes = new ArrayList<>();
    private RecipeAdapter adapter;
    private TextView empty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_suggestions, container, false);
        empty = v.findViewById(R.id.tvEmpty);
        RecyclerView rv = v.findViewById(R.id.rvSuggestions);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecipeAdapter(r -> {
            Intent i = new Intent(getActivity(), RecipeDetailActivity.class);
            i.putExtra("recipeId", r.getId());
            startActivity(i);
        });
        rv.setAdapter(adapter);

        loadPantry();
        new RecipeRepository().listen((snap, e) -> {
            recipes.clear();
            if (snap != null)
                for (QueryDocumentSnapshot d : snap) recipes.add(d.toObject(Recipe.class));
            update();
        });

        return v;
    }

    private void loadPantry() {
        new PantryRepository().listen((snap, e) -> {
            pantry.clear();
            if (snap != null) for (QueryDocumentSnapshot d : snap) {
                PantryItem x = d.toObject(PantryItem.class);
                x.setId(d.getId());
                pantry.add(x);
            }
            update();
        });
    }

    private void update() {
        if (recipes.isEmpty()) {
            adapter.setRecipes(new ArrayList<>(), pantry);
            empty.setVisibility(View.VISIBLE);
            return;
        }

        List<Recipe> candidates = new ArrayList<>(recipes);

        Collections.sort(candidates, (r1, r2) -> {
            int match1 = RecipeMatcher.countMatchingIngredients(r1, pantry);
            int match2 = RecipeMatcher.countMatchingIngredients(r2, pantry);
            int total1 = r1.getIngredients() != null ? r1.getIngredients().size() : 1;
            int total2 = r2.getIngredients() != null ? r2.getIngredients().size() : 1;

            boolean full1 = match1 == total1 && total1 > 0;
            boolean full2 = match2 == total2 && total2 > 0;

            if (full1 && !full2) return -1;
            if (!full1 && full2) return 1;

            if (match1 != match2) return Integer.compare(match2, match1);

            double ratio1 = (double) match1 / total1;
            double ratio2 = (double) match2 / total2;
            return Double.compare(ratio2, ratio1);
        });

        List<Recipe> suggested = new ArrayList<>();
        for (Recipe r : candidates) {
            int matched = RecipeMatcher.countMatchingIngredients(r, pantry);
            if (matched > 0) {
                suggested.add(r);
            }
        }

        adapter.setRecipes(suggested, pantry);
        empty.setVisibility(suggested.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
