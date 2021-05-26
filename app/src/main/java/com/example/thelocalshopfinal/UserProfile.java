package com.example.thelocalshopfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thelocalshopfinal.Login;
import com.example.thelocalshopfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserProfile extends AppCompatActivity {
    Button sign_out;
    Button Edit;
    TextView nameTV;
    TextView emailTV;
    TextView contactTV;
    TextView addressTV;
    ImageView back;
    String Name="",pass,contact="",address="",email,currentuser;
    FirebaseAuth mAuth;
    boolean isStore=false;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sign_out = findViewById(R.id.log_out);
        nameTV = findViewById(R.id.name);
        emailTV = findViewById(R.id.email);
        contactTV=findViewById(R.id.contact);
        back=findViewById(R.id.backfromprofile);
        addressTV=findViewById(R.id.address);
        Edit =findViewById(R.id.EditProfile);mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String user_id=user.getUid();

        if(FirebaseAuth.getInstance().getCurrentUser()==null) {
            signOut();
        }

        db.collection("customer").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        nameTV.setText(task.getResult().getString("fName"));
                        emailTV.setText(task.getResult().getString("email"));
                        contactTV.setText(task.getResult().getString("phone"));
                        addressTV.setText(task.getResult().getString("address"));
                        isStore=false;
                    }
                    else{
                        db.collection("store").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    if(task.getResult().exists()){
                                        nameTV.setText(task.getResult().getString("StoreName"));
                                        emailTV.setText(task.getResult().getString("email"));
                                        contactTV.setText(task.getResult().getString("phone"));
                                        addressTV.setVisibility(View.GONE);
                                        isStore=true;
                                    }
                                    else{

                                    }

                                    //  addressTV.setText(task.getResult().getString(""));
                                }
                                else{
                                    Toast.makeText(UserProfile.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                  //  addressTV.setText(task.getResult().getString(""));
                }
                else{
                    Toast.makeText(UserProfile.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });



        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(isStore){
                Intent i =new Intent(UserProfile.this,StoreMainActivity.class);
                startActivity(i);
                finish();

            }
            else{
                Intent i =new Intent(UserProfile.this,MainActivity.class);
                startActivity(i);
                finish();

            }

        }
    });


        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(UserProfile.this,EditDetails.class);
                i.putExtra("isStore",isStore);
                startActivity(i);
                finish();
            }
        });
    }

    private void signOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(UserProfile.this);
        alert.setMessage("Are you sure you want to logout?").setCancelable(false).setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(UserProfile.this, Login.class);
                startActivity(in);
                finish();
                Toast.makeText(UserProfile.this,"Logged out Successfully",Toast.LENGTH_LONG).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }

}
