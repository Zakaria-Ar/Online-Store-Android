package com.example.projetmobile_cart;

import static com.example.projetmobile_cart.R.id.fill_out_form_textview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StoreFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ProgressBar progressBar;
    private int progressCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_form);

        // Initialize views
        TextView fillOutFormTextView = findViewById(fill_out_form_textview);
        Spinner productTypeSpinner = findViewById(R.id.product_type_spinner);
        EditText productNameEditText = findViewById(R.id.product_name_edittext);
        EditText productDescriptionEditText = findViewById(R.id.product_description_edittext);
        EditText productPriceEditText = findViewById(R.id.product_price_edittext);
        Button submitButton = findViewById(R.id.submit_button);


        // Set up spinner
        productTypeSpinner.setOnItemSelectedListener(this);
        List<String> productTypes = new ArrayList<>();
        productTypes.add("Téléphones portables");
        productTypes.add("Vetements");
        productTypes.add("Accessoires");
        productTypes.add("Sport");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productTypeSpinner.setAdapter(adapter);

        // Set up submit button click listener
        submitButton.setOnClickListener(view -> {

        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}