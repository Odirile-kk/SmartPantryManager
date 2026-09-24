package com.smartpantry.manager.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class PantryItem {
    private String id, name, expiryDate;

    public PantryItem() {}

    public PantryItem(String id, String name, String expiryDate) {
        this.id = id; this.name = name; this.expiryDate = expiryDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public Map<String, Object> toMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("name", name); map.put("expiryDate", expiryDate);
        return map;
    }
}
