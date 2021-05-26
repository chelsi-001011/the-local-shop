package com.example.thelocalshopfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StoreMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
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
                    startActivity(new Intent(StoreMainActivity.this, StoreMainActivity.class));
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
}