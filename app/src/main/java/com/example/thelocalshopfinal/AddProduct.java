package com.example.thelocalshopfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {

    FirebaseAuth fAuth;
    EditText productCost;
    EditText productName;
    Button addBtn;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        fAuth = FirebaseAuth.getInstance();
        productCost = findViewById(R.id.tvproductCost);
        productName = findViewById(R.id.tvproductName);
        addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebaseUser currentUser = fAuth.getCurrentUser();
//                if (currentUser != null) {
                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("store").document(userID).collection("products").document(productName.getText().toString());
                    Map<String, Object> product = new HashMap<>();
                    product.put("product_name", productName.getText().toString());
                    product.put("product_cost", productCost.getText().toString());
                    documentReference.set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                startActivity(new Intent(getApplicationContext(), StoreMainActivity.class));
//                } else {
//                    Toast.makeText(AddProduct.this, "ERROR! ", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}