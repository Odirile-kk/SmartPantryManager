package com.smartpantry.manager.controller;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smartpantry.manager.model.PantryItem;

public class PantryRepository {
    private final CollectionReference pantry = FirebaseFirestore.getInstance().collection("pantry");

    public void listen(com.google.firebase.firestore.EventListener<com.google.firebase.firestore.QuerySnapshot> listener) {
        pantry.addSnapshotListener(listener);
    }

    public void save(PantryItem item) {
        if (item.getId() == null || item.getId().isEmpty()) pantry.add(item.toMap());
        else pantry.document(item.getId()).set(item.toMap());
    }

    public void delete(String id) { pantry.document(id).delete(); }
}
