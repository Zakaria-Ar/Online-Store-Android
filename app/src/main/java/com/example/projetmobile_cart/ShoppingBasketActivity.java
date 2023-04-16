package com.example.projetmobile_cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);

        recyclerView = findViewById(R.id.recyclerview);
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);

        // Ajoutez des éléments à itemList pour qu'ils soient affichés dans RecyclerView
        itemList.add(new Item(R.drawable.item1, "Item 1", "Description 1", "10$","x1"));
        itemList.add(new Item(R.drawable.item2, "Item 2", "Description 2", "15$","x2"));
        itemList.add(new Item(R.drawable.item3, "Item 3", "Description 3", "20$","x1"));
        itemList.add(new Item(R.drawable.item4, "Item 4", "Description 4", "25$","x3"));
        itemList.add(new Item(R.drawable.item5, "Item 5", "Description 5", "30$","x5"));

}
}