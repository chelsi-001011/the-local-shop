package com.example.thelocalshopfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class StoresScreen extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        String StoreType = "";
        if (extras != null) {
            StoreType = extras.getString("StoreType");
            //The key argument here must match that used in the other activity
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_screen);

        firebaseFirestore=FirebaseFirestore.getInstance();
        storeList =  findViewById(R.id.stores);

        Query query = firebaseFirestore.collection("store").whereEqualTo("category", StoreType);
        FirestoreRecyclerOptions<StoresModel> options=new FirestoreRecyclerOptions.Builder<StoresModel>()
                .setQuery(query, StoresModel.class)
                .build();
        adapter= new FirestoreRecyclerAdapter<StoresModel, StoresViewHolder>(options) {
            @NonNull
            @Override
            public StoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_store, parent, false);
                return new StoresViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull StoresViewHolder holder, int position, @NonNull StoresModel model) {
                List<Address> addresses = null;
                Geocoder geocoder = new Geocoder(getApplicationContext() ,Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(model.getLatitude(), model.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String postalCode = addresses.get(0).getPostalCode();

                String add= "Address: "+city+", "+state+", "+postalCode;
                String cont="Contact Us: "+model.getPhone();

                /*Location startPoint=new Location("locationA");
                startPoint.setLatitude(model.getLatitude());
                startPoint.setLongitude(model.getLongitude());

                Location endPoint=new Location("locationA");
                endPoint.setLatitude(17.375775);
                endPoint.setLongitude(78.469218);

                double distance=startPoint.distanceTo(endPoint);*/

                holder.store_name.setText(model.getStoreName());
                holder.store_address.setText(add);
                holder.store_contact.setText(cont);
            }
        };

        storeList.setHasFixedSize(true);
        storeList.setLayoutManager(new LinearLayoutManager(this));
        storeList.setAdapter(adapter);
    }

    private class StoresViewHolder extends RecyclerView.ViewHolder{

        private TextView store_name;
        private TextView store_address;
        private TextView store_contact;

        public StoresViewHolder(@NonNull View itemView) {
            super(itemView);

            store_name=itemView.findViewById(R.id.store_name);
            store_address=itemView.findViewById(R.id.store_address);
            store_contact=itemView.findViewById(R.id.store_contact);
        }
    }

    @Override
    protected  void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}