package com.smartpantry.manager.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpantry.manager.R;
import com.smartpantry.manager.model.PantryItem;

import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.model.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.Holder> {
    public interface Listener {
        void open(Recipe recipe);
    }

    private List<Recipe> recipes = new ArrayList<>();
    private List<PantryItem> pantry = new ArrayList<>();
    private final Listener listener;

    public RecipeAdapter(Listener l) {
        listener = l;
    }

    public void setRecipes(List<Recipe> r, List<PantryItem> pantry) {
        this.recipes = r != null ? r : new ArrayList<>();
        this.pantry = pantry != null ? pantry : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    public Holder onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new Holder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_recipe, p, false));
    }

    public void onBindViewHolder(@NonNull Holder h, int pos) {
        Recipe r = recipes.get(pos);
        h.name.setText(r.getName());

        int total = r.getIngredients() != null ? r.getIngredients().size() : 0;
        int matched = RecipeMatcher.countMatchingIngredients(r, pantry);
        int missing = Math.max(0, total - matched);

        if (missing == 0 && total > 0) {
            h.summary.setText("All " + total + " ingredients in pantry • Ready to cook!");
        } else {
            h.summary.setText("You have " + matched + " of " + total + " ingredients (missing " + missing + ")");
        }

        h.itemView.setOnClickListener(v -> listener.open(r));
    }

    public int getItemCount() {
        return recipes.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView name, summary;

        Holder(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            summary = v.findViewById(R.id.tvSummary);
        }
    }
}
