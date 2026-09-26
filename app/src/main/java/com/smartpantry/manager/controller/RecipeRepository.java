package com.smartpantry.manager.controller;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.smartpantry.manager.model.Recipe;
import java.util.List;

public class RecipeRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void seedIfNeeded(List<Recipe> recipes, Runnable done) {
        db.collection("recipes").get().addOnSuccessListener(snapshot -> {
            com.google.firebase.firestore.WriteBatch batch = db.batch();
            if (snapshot != null) {
                for (QueryDocumentSnapshot doc : snapshot) {
                    batch.delete(doc.getReference());
                }
            }
            for (Recipe r : recipes) {
                batch.set(db.collection("recipes").document(r.getId()), r);
            }
            batch.commit().addOnCompleteListener(t -> done.run());
        }).addOnFailureListener(e -> done.run());
    }

    public void listen(com.google.firebase.firestore.EventListener<com.google.firebase.firestore.QuerySnapshot> listener) {
        db.collection("recipes").addSnapshotListener(listener);
    }
}
