package com.example.thelocalshopfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeLineStore extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adap;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        recyclerView = findViewById(R.id.rview_for_timeline);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(TimeLineStore.this);

        recyclerView.setLayoutManager(layoutManager);
        final List<TimelineRestStruct> list = new ArrayList<>();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        final String name = documentSnapshot.getString("customerName");
                        final String status = documentSnapshot.getString("status");
                        final String cost = documentSnapshot.getString("cost");
                        final TimelineRestStruct rest = new TimelineRestStruct(cost,status,name);
                        list.add(rest);
                    }
                    Collections.reverse(list);
                    adap = new TimelineAdapter(list, TimeLineStore.this);
                    recyclerView.setAdapter(adap);
                }
                else{
                    Toast.makeText(TimeLineStore.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();
            switch (id){
                case R.id.nav_home:
                    startActivity(new Intent(TimeLineStore.this, MainActivity.class));
                    break;
                case R.id.nav_map:
                    startActivity(new Intent(TimeLineStore.this, MapActivity.class));
                    break;
                case R.id.nav_profile:
                    startActivity(new Intent(TimeLineStore.this, UserProfile.class));
                    break;
                case R.id.nav_cart:
                    startActivity(new Intent(TimeLineStore.this, TimeLineCustomer.class));
                    break;
                default:
                    break;
                // case R.id.nav_cart:
                //     break;

            }
            return true;
        }
    };

}
