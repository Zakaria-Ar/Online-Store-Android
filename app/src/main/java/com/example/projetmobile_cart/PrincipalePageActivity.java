package com.example.projetmobile_cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PrincipalePageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<DataClass> dataList;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale_page);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        dataList = new ArrayList<>();
        adapter = new MyAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        // Fetch new products from Firebase Realtime Database
        FirebaseDatabase.getInstance().getReference("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataList.clear();
                        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                            DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                            if (dataClass != null) {
                                dataClass.setKey(itemSnapshot.getKey());
                                dataList.add(dataClass);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(PrincipalePageActivity.this,
                                "Failed to fetch products: " + databaseError.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

        View account = findViewById(R.id.menuAccount);
        account.setOnClickListener(v -> startActivity(new Intent(PrincipalePageActivity.this, ProfileActivity.class)));

        account = findViewById(R.id.menuFavorite);
        account.setOnClickListener(v -> startActivity(new Intent(PrincipalePageActivity.this, ShoppingBasketActivity.class)));

        account = findViewById(R.id.menuCreateStore);
        account.setOnClickListener(v -> startActivity(new Intent(PrincipalePageActivity.this, ImportActivity.class)));

        account = findViewById(R.id.menuSettings);
        account.setOnClickListener(v -> startActivity(new Intent(PrincipalePageActivity.this, AccountInformationActivity.class)));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userID = user.getUid();

        final TextView userName = findViewById(R.id.textUserName);
        FirebaseDatabase.getInstance().getReference("Users").child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // User document exists, get data
                            String name = dataSnapshot.child("Last Name").getValue(String.class);
                            userName.setText(name);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(PrincipalePageActivity.this,
                                "Failed to fetch user data: " + databaseError.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
