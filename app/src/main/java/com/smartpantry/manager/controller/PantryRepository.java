package com.smartpantry.manager.controller;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smartpantry.manager.model.PantryItem;

public class PantryRepository {
    private final CollectionReference pantry = FirebaseFirestore.getInstance().collection("pantry");

    public void listen(com.google.firebase.firestore.EventListener<com.google.firebase.firestore.QuerySnapshot> listener) {
        pantry.addSnapshotListener(listener);
    }

    public Task<Void> save(PantryItem item) {
        if (item.getId() == null || item.getId().isEmpty()) {
            return pantry.add(item.toMap()).continueWith(task -> null);
        } else {
            return pantry.document(item.getId()).set(item.toMap());
        }
    }

    public Task<Void> update(PantryItem item) {
        if (item.getId() != null && !item.getId().isEmpty()) {
            return pantry.document(item.getId()).set(item.toMap());
        }
        return save(item);
    }

    public Task<Void> delete(String id) {
        if (id != null && !id.isEmpty()) {
            return pantry.document(id).delete();
        }
        return Tasks.forResult(null);
    }
}
