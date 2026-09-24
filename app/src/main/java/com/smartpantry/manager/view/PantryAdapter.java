package com.smartpantry.manager.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpantry.manager.R;
import com.smartpantry.manager.model.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.Holder> {
    public interface Listener {
        void edit(PantryItem x);

        void update(PantryItem x);

        void delete(PantryItem x);
    }

    private List<PantryItem> items = new ArrayList<>();
    private final Listener listener;

    public PantryAdapter(Listener l) {
        listener = l;
    }

    public void setItems(List<PantryItem> x) {
        items = x;
        notifyDataSetChanged();
    }

    @NonNull
    public Holder onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new Holder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_pantry, p, false));
    }

    public void onBindViewHolder(@NonNull Holder h, int pos) {
        PantryItem x = items.get(pos);
        h.name.setText(x.getName());
        h.expiry.setText(x.getExpiryDate() == null || x.getExpiryDate().isEmpty() ? "No expiry date" : "Expires: " + x.getExpiryDate());
        h.edit.setOnClickListener(v -> listener.edit(x));
        h.update.setOnClickListener(v -> listener.update(x));
        h.del.setOnClickListener(v -> listener.delete(x));
    }

    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView name, expiry;
        ImageButton edit, update, del;

        Holder(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            expiry = v.findViewById(R.id.tvExpiry);
            edit = v.findViewById(R.id.btnEdit);
            update = v.findViewById(R.id.btnUpdate);
            del = v.findViewById(R.id.btnDelete);
        }
    }
}
