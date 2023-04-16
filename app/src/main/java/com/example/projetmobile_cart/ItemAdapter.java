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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = itemList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.nameTextView.setText(currentItem.getName());
        holder.descriptionTextView.setText(currentItem.getDescription());
        holder.priceTextView.setText(currentItem.getPrice());
        holder.quaTextview.setText(currentItem.getQuality());
    }

    @Override
    public int getItemCount() {
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
            imageView = itemView.findViewById(R.id.imageay);
            nameTextView = itemView.findViewById(R.id.textView1ay);
            descriptionTextView = itemView.findViewById(R.id.textView2ay);
            priceTextView =itemView.findViewById(R.id.textView3ay);
            quaTextview=itemView.findViewById(R.id.textViewqua);
        }
    }
}