package com.smartpantry.manager.controller;

import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeSeeder {
    private static Recipe r(String id, String name, String[] ing, String... steps) {
        List<RecipeIngredient> list = new ArrayList<>();
        for (String x : ing) list.add(new RecipeIngredient(x));
        return new Recipe(id, name, list, Arrays.asList(steps));
    }

    public static List<Recipe> all() {
        List<Recipe> x = new ArrayList<>();
        x.add(r("pasta_tomato", "Tomato Pasta", new String[]{"pasta", "tomato"}, "Boil pasta.", "Sauté tomatoes into sauce.", "Mix together and serve."));
        x.add(r("grilled_cheese", "Grilled Cheese", new String[]{"bread", "cheese", "butter"}, "Butter the bread.", "Add cheese between slices.", "Toast both sides until golden."));
        x.add(r("pancakes", "Simple Pancakes", new String[]{"flour", "milk", "egg", "sugar"}, "Mix dry ingredients.", "Whisk in milk and eggs.", "Cook small portions in a hot pan.", "Serve warm."));
        x.add(r("scrambled_eggs", "Creamy Scrambled Eggs", new String[]{"egg", "milk", "butter"}, "Whisk eggs with milk.", "Melt butter in a pan.", "Cook eggs gently while stirring.", "Serve immediately."));
        x.add(r("salad", "Fresh Garden Salad", new String[]{"lettuce", "tomato", "cucumber", "olive oil"}, "Chop vegetables.", "Combine in a bowl.", "Drizzle with olive oil and serve."));
        return x;
    }
}
