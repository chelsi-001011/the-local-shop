package com.example.thelocalshopfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
    public void profile(View view){
        startActivity(new Intent(getApplicationContext(),UserProfile.class));
    }

    public void showMap(View view){
        startActivity(new Intent(getApplicationContext(),MapActivity.class));
//        finish();
    }
}