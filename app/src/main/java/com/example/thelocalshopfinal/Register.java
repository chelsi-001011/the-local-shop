package com.example.thelocalshopfinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    Button customerBtn,storeBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        customerBtn = findViewById(R.id.customerBtn);
        storeBtn = findViewById(R.id.storeBtn);
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            DocumentReference docIdRef = rootRef.collection("customer").document(mAuth.getCurrentUser().getUid());
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Intent intent=new Intent(Register.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent=new Intent(Register.this,StoreMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(Register.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Register.this, Register_Customer.class));
                    finish();

                }
                catch(Exception e){
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    startActivity(new Intent(Register.this, Register_Store.class));
                    finish();

                }
                catch(Exception e){
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}