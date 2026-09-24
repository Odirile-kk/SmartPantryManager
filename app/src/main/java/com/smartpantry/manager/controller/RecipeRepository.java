package com.smartpantry.manager.controller;

import com.google.firebase.firestore.FirebaseFirestore;
import com.smartpantry.manager.model.Recipe;
import java.util.List;

public class RecipeRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void seedIfNeeded(List<Recipe> recipes, Runnable done) {
        com.google.firebase.firestore.WriteBatch batch = db.batch();
        for (Recipe r : recipes) {
            batch.set(db.collection("recipes").document(r.getId()), r);
        }
        batch.commit().addOnCompleteListener(t -> done.run());
    }

    public void listen(com.google.firebase.firestore.EventListener<com.google.firebase.firestore.QuerySnapshot> listener) {
        db.collection("recipes").addSnapshotListener(listener);
    }
}
