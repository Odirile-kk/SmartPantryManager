package com.smartpantry.manager.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String id, name;
    private List<RecipeIngredient> ingredients = new ArrayList<>();
    private List<String> steps = new ArrayList<>();

    public Recipe() {}
    public Recipe(String id, String name, List<RecipeIngredient> ingredients, List<String> steps) {
        this.id=id; this.name=name; this.ingredients=ingredients; this.steps=steps;
    }
    public String getId(){return id;}
    public String getName(){return name;}
    public List<RecipeIngredient> getIngredients(){return ingredients;}
    public List<String> getSteps(){return steps;}
    public void setId(String id){this.id=id;}
    public void setName(String name){this.name=name;}
    public void setIngredients(List<RecipeIngredient> ingredients){this.ingredients=ingredients;}
    public void setSteps(List<String> steps){this.steps=steps;}
}
