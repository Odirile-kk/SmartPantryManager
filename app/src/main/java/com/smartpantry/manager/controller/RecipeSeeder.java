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
        x.add(r("omelette", "Garden Omelette", new String[]{"egg", "milk", "onion", "cheese"}, "Beat eggs with milk.", "Cook onion briefly.", "Add egg mixture and cheese.", "Fold and serve."));
        x.add(r("pancakes", "Simple Pancakes", new String[]{"flour", "milk", "egg", "sugar"}, "Mix dry ingredients.", "Whisk in milk and eggs.", "Cook small portions in a hot pan.", "Serve warm."));
        x.add(r("fried_rice", "Vegetable Fried Rice", new String[]{"rice", "egg", "carrot", "peas", "soy sauce"}, "Cook or use cold cooked rice.", "Scramble eggs in a hot pan.", "Stir-fry vegetables.", "Add rice and soy sauce and toss."));
        x.add(r("grilled_cheese", "Grilled Cheese", new String[]{"bread", "cheese", "butter"}, "Butter the bread.", "Add cheese between slices.", "Toast both sides until golden."));
        x.add(r("chicken_rice", "Chicken Rice Bowl", new String[]{"chicken", "rice", "carrot", "soy sauce"}, "Cook rice.", "Cook chicken thoroughly.", "Stir-fry carrot.", "Combine and season with soy sauce."));
        x.add(r("tomato_soup", "Tomato Soup", new String[]{"tomato", "onion", "garlic", "olive oil"}, "Sauté onion and garlic.", "Add tomatoes and simmer.", "Blend until smooth.", "Finish with olive oil."));
        x.add(r("banana_smoothie", "Banana Smoothie", new String[]{"banana", "milk", "honey"}, "Add everything to a blender.", "Blend until smooth.", "Serve immediately."));
        x.add(r("salad", "Fresh Garden Salad", new String[]{"lettuce", "tomato", "cucumber", "olive oil"}, "Chop vegetables.", "Combine in a bowl.", "Drizzle with olive oil."));
        x.add(r("garlic_bread", "Garlic Bread", new String[]{"bread", "butter", "garlic"}, "Mix softened butter with garlic.", "Spread over bread.", "Bake until crisp."));
        x.add(r("mac_cheese", "Quick Mac and Cheese", new String[]{"pasta", "milk", "cheese", "butter"}, "Cook pasta.", "Warm milk and butter.", "Stir in cheese.", "Combine with pasta."));
        x.add(r("french_toast", "French Toast", new String[]{"bread", "egg", "milk", "sugar"}, "Whisk eggs, milk and sugar.", "Dip bread in mixture.", "Cook both sides until golden."));
        x.add(r("bean_wrap", "Bean Wrap", new String[]{"tortilla", "beans", "cheese", "tomato"}, "Warm tortillas.", "Fill with beans, cheese and tomato.", "Fold and toast lightly."));
        x.add(r("apple_oats", "Apple Oatmeal", new String[]{"oats", "milk", "apple", "honey"}, "Simmer oats in milk.", "Add chopped apple.", "Sweeten with honey."));
        x.add(r("curry_chickpea", "Chickpea Curry", new String[]{"chickpeas", "tomato", "onion", "garlic", "olive oil"}, "Sauté onion and garlic.", "Add tomatoes and simmer.", "Add chickpeas and cook through.", "Finish with olive oil."));
        x.add(r("tuna_sandwich", "Tuna Sandwich", new String[]{"bread", "tuna", "mayonnaise", "lettuce"}, "Mix tuna and mayonnaise.", "Layer with lettuce between bread.", "Slice and serve."));
        x.add(r("scrambled_eggs", "Creamy Scrambled Eggs", new String[]{"egg", "milk", "butter"}, "Whisk eggs with milk.", "Melt butter.", "Cook eggs gently while stirring.", "Serve immediately."));
        x.add(r("vegetable_pasta", "Vegetable Pasta", new String[]{"pasta", "carrot", "tomato", "olive oil"}, "Boil pasta.", "Sauté carrot and tomato.", "Add pasta and olive oil.", "Toss and serve."));
        return x;
    }
}
