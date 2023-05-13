package com.example.projetmobile_cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetmobile_cart.Basket;
import com.example.projetmobile_cart.R;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
    private List<Basket> basketList;

    public BasketAdapter(List<Basket> basketList) {
        this.basketList = basketList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Basket basket = basketList.get(position);
        holder.bindBasket(basket);
    }

    @Override
    public int getItemCount() {
        return basketList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView priceTextView;
        private TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.basketTitle);
            priceTextView = itemView.findViewById(R.id.basketPrice);
            descriptionTextView = itemView.findViewById(R.id.basketDesc);
        }

        public void bindBasket(Basket basket) {
            titleTextView.setText(basket.getDataTitle());
            priceTextView.setText(basket.getDataPrice());
            descriptionTextView.setText(basket.getDataDesc());
        }
    }
}
