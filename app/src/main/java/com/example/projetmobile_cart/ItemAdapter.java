package com.example.projetmobile_cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the item layout and creates a new ViewHolder instance
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Binds the data to the views within the ViewHolder
        Item currentItem = itemList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.nameTextView.setText(currentItem.getName());
        holder.descriptionTextView.setText(currentItem.getDescription());
        holder.priceTextView.setText(currentItem.getPrice());
        holder.quaTextview.setText(currentItem.getQuality());
    }

    @Override
    public int getItemCount() {
        // Returns the number of items in the data list
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView priceTextView;
        public TextView quaTextview;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initializes the views within the ViewHolder
            imageView = itemView.findViewById(R.id.favoriteImage);
            nameTextView = itemView.findViewById(R.id.favoriteTitle);
            descriptionTextView = itemView.findViewById(R.id.favoriteDesc);
            priceTextView = itemView.findViewById(R.id.favoritePrice);
            quaTextview = itemView.findViewById(R.id.favoriteDesc);
        }
    }
}
