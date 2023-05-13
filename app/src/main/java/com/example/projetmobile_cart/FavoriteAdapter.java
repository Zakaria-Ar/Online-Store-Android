package com.example.projetmobile_cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Favorite> favoriteList;

    public FavoriteAdapter(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Favorite favorite = favoriteList.get(position);

        holder.favoriteTitle.setText(favorite.getDataTitle());
        holder.favoriteDesc.setText(favorite.getDataDesc());
        holder.favoritePrice.setText(favorite.getDataPrice());

        // Load image using Glide or any other image loading library
        Glide.with(holder.itemView.getContext()).load(favorite.getDataImage()).into(holder.favoriteImage);
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView favoriteImage;
        TextView favoriteTitle, favoriteDesc, favoritePrice;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            favoriteImage = itemView.findViewById(R.id.favoriteImage);
            favoriteTitle = itemView.findViewById(R.id.favoriteTitle);
            favoriteDesc = itemView.findViewById(R.id.favoriteDesc);
            favoritePrice = itemView.findViewById(R.id.favoritePrice);
        }
    }
}
