package com.example.thelocalshopfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();
            switch (id){
                case R.id.nav_home:
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    break;
                case R.id.nav_map:
                    startActivity(new Intent(MainActivity.this, MapActivity.class));
                    break;
                 case R.id.nav_profile:
                     startActivity(new Intent(MainActivity.this, UserProfile.class));
                     break;
                case R.id.nav_cart:
                    startActivity(new Intent(MainActivity.this, TimeLineCustomer.class));
                    break;
                default:
                    break;
                // case R.id.nav_cart:
                //     break;

            }
            return true;
        }
    };

    public void showMedicalStores(View view){
        String value="Medical";
        Intent intent = new Intent(getApplicationContext(),StoresScreen.class);
        intent.putExtra("StoreType", value);
        startActivity(intent);
//      finish();
    }

    public void showGroceryStores(View view){
        String value="Grocery";
        Intent intent = new Intent(getApplicationContext(),StoresScreen.class);
        intent.putExtra("StoreType", value);
        startActivity(intent);
//      finish();
    }

    public void showFoodStores(View view){
        String value="Food";
        Intent intent = new Intent(getApplicationContext(),StoresScreen.class);
        intent.putExtra("StoreType", value);
        startActivity(intent);
//      finish();
    }

    public void showServicesStores(View view){
        String value="Services";
        Intent intent = new Intent(getApplicationContext(),StoresScreen.class);
        intent.putExtra("StoreType", value);
        startActivity(intent);
//      finish();
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}