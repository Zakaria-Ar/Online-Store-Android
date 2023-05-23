package com.example.projetmobile_cart;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
    private List<Basket> basketList;

    public BasketAdapter(List<Basket> basketList) {
        this.basketList = basketList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind the data of the corresponding Basket object to the ViewHolder
        Basket basket = basketList.get(position);
        holder.bindBasket(basket);
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the RecyclerView
        return basketList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleTextView;
        private TextView priceTextView;
        private TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views in the ViewHolder
            imageView = itemView.findViewById(R.id.basketImage);
            titleTextView = itemView.findViewById(R.id.basketTitle);
            priceTextView = itemView.findViewById(R.id.basketPrice);
            descriptionTextView = itemView.findViewById(R.id.basketDesc);
        }

        public void bindBasket(Basket basket) {
            // Bind the data of the Basket object to the views in the ViewHolder
            titleTextView.setText(basket.getDataTitle());
            priceTextView.setText(basket.getDataPrice());
            descriptionTextView.setText(basket.getDataDesc());

            // Load the image using Glide into the ImageView
            Glide.with(itemView.getContext())
                    .load(basket.getDataImage())
                    .into(imageView);
        }
    }
}
