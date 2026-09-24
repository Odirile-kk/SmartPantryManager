package com.smartpantry.manager.model;

import java.util.List;
import java.util.Locale;

public class RecipeMatcher {

    public static boolean matches(Recipe recipe, List<PantryItem> pantry) {
        if (recipe == null || recipe.getIngredients() == null || recipe.getIngredients().isEmpty()) return false;
        for (RecipeIngredient required : recipe.getIngredients()) {
            PantryItem available = find(required.getName(), pantry);
            if (available == null) {
                return false;
            }
        }
        return true;
    }

    public static int countMatchingIngredients(Recipe recipe, List<PantryItem> pantry) {
        if (recipe == null || recipe.getIngredients() == null) return 0;
        int count = 0;
        for (RecipeIngredient required : recipe.getIngredients()) {
            if (find(required.getName(), pantry) != null) {
                count++;
            }
        }
        return count;
    }

    public static PantryItem find(String wanted, List<PantryItem> pantry) {
        if (wanted == null || pantry == null) return null;
        String target = normalize(wanted);
        if (target.isEmpty()) return null;

        for (PantryItem item : pantry) {
            if (item == null || item.getName() == null) continue;
            String available = normalize(item.getName());
            if (isMatch(target, available)) {
                return item;
            }
        }
        return null;
    }

    public static boolean isMatch(String req, String avail) {
        if (req.equals(avail)) return true;
        if (req.contains(avail) || avail.contains(req)) return true;

        String[] reqWords = req.split("\\s+");
        String[] availWords = avail.split("\\s+");
        for (String w1 : reqWords) {
            for (String w2 : availWords) {
                if (w1.length() > 2 && w2.length() > 2 && (w1.equals(w2) || w1.contains(w2) || w2.contains(w1))) {
                    return true;
                }
            }
        }
        return false;
    }

    static String normalize(String value) {
        if (value == null) return "";
        String s = value.trim().toLowerCase(Locale.US).replaceAll("[^a-z0-9\\s]", "");
        if (s.endsWith("ies") && s.length() > 4) return s.substring(0, s.length() - 3) + "y";
        if (s.endsWith("oes") && s.length() > 4) return s.substring(0, s.length() - 2);
        if (s.endsWith("ses") && s.length() > 4) return s.substring(0, s.length() - 1);
        if (s.endsWith("s") && !s.endsWith("ss") && !s.endsWith("us") && !s.endsWith("is") && s.length() > 3) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }
}
