package com.example.thelocalshopfinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class StoreMainActivity extends AppCompatActivity {

    private RecyclerView productlist;
    FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private FirestoreRecyclerAdapter adapter;
    private FloatingActionButton addProduct;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        fstore = FirebaseFirestore.getInstance();
        addProduct = findViewById(R.id.addProductBtn);
        fAuth = FirebaseAuth.getInstance();
        productlist = findViewById(R.id.products);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StoreMainActivity.this, AddProduct.class));
                finish();
            }
        });
        userID = fAuth.getCurrentUser().getUid();
            Query query = fstore.collection("store").document(userID).collection("products");

            FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                    .setQuery(query,ProductsModel.class)
                    .build();

             adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
                @NonNull
                @Override
                public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product,parent,false);
                    return new ProductsViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
                    holder.productName.setText(model.getProductName());
                    holder.productCost.setText(model.getProductCost());
                    holder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productlist.removeItemDecorationAt(position);
                            fstore.collection("store").document(userID).collection("products").document(holder.productName.getText().toString()).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG", "Product successfully deleted!");
                                        }
                                    });
                        }
                    });
                }
            };
            productlist.setHasFixedSize(true);
            productlist.setLayoutManager(new LinearLayoutManager(this));
            productlist.setAdapter(adapter);
        }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();
            switch (id){
                case R.id.nav_home:
                    startActivity(new Intent(StoreMainActivity.this, StoreMainActivity.class));
                    break;
                case R.id.nav_cart:
                    startActivity(new Intent(StoreMainActivity.this, TimeLineStore.class));
                    break;
                case R.id.nav_profile:
                    startActivity(new Intent(StoreMainActivity.this, UserProfile.class));
                    break;
                default:
                    break;
                // case R.id.nav_cart:
                //     break;

            }
            return true;
        }
    };

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productCost;
        private Button delete;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.button);
            productName = itemView.findViewById(R.id.product_name);
            productCost = itemView.findViewById(R.id.product_cost);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}