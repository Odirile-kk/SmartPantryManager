package com.smartpantry.manager.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class RecipeIngredient {
    private String name;

    public RecipeIngredient() {}
    public RecipeIngredient(String name) {
        this.name = name;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
