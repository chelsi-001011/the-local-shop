package com.example.thelocalshopfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CustomerProducts extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private  RecyclerView customerProducts;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        String StrName ="";
        if (extras != null) {
            StrName = extras.getString("StoreName");
            //The key argument here must match that used in the other activity
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_products);
        firebaseFirestore = FirebaseFirestore.getInstance();
        customerProducts=findViewById(R.id.customerProducts);

        CollectionReference stores = firebaseFirestore.collection("store");
        final String[] storeId = new String[1];
        Query nameQuery = stores.whereEqualTo("StoreName", StrName);
        nameQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        storeId[0] =document.getId();
                        Log.d("TAG",storeId[0]);
                    }
                }
            }
        });

        Query query = firebaseFirestore.collection("store").document("6KKwBh1vdycQmXjNOBIAyrt31il1").collection("products");
        FirestoreRecyclerOptions<CustomerProductsModel> options=new FirestoreRecyclerOptions.Builder<CustomerProductsModel>()
                .setQuery(query, CustomerProductsModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<CustomerProductsModel, CustomerProductsViewHolder>(options) {
            @NonNull
            @Override
            public CustomerProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customer_product, parent, false);
                return new CustomerProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CustomerProductsViewHolder holder, int position, @NonNull CustomerProductsModel model) {
                holder.product_name.setText(model.getProduct_name());
                holder.product_cost.setText(model.getProduct_cost());
            }
        };

        customerProducts.setHasFixedSize(true);
        customerProducts.setLayoutManager(new LinearLayoutManager(this));
        customerProducts.setAdapter(adapter);
    }

    private class CustomerProductsViewHolder extends RecyclerView.ViewHolder{

        private TextView product_name;
        private TextView product_cost;

        public CustomerProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_cost = itemView.findViewById(R.id.product_cost);

        }

    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
}