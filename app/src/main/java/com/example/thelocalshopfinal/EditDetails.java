package com.example.thelocalshopfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thelocalshopfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class EditDetails extends AppCompatActivity {
    EditText NameTF,ContTF,AddTF;
    Button Modify;
    String username,Uid;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        NameTF=findViewById(R.id.ChangeName);
        ContTF=findViewById(R.id.ChangeContact);
        AddTF=findViewById(R.id.ChangeAddress);
        Modify=findViewById(R.id.ChangeButton);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
        String user_id=user.getUid();
        db.collection("customer").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    NameTF.setText(task.getResult().getString("fName"));
                    ContTF.setText(task.getResult().getString("phone"));
                    //  addressTV.setText(task.getResult().getString(""));
                }
                else{
                    Toast.makeText(EditDetails.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newName = NameTF.getText().toString().trim();
                final String newContact = ContTF.getText().toString().trim();
                final String newAdd = AddTF.getText().toString().trim();
                if(mAuth.getCurrentUser()==null) {
                    mAuth.signOut();
                    Intent in = new Intent(EditDetails.this, Login.class);
                    startActivity(in);
                    finish();
                }
                else{
                    try {
                        if(isValidContact(newContact)&&isValidName(newName)){
                            final ProgressDialog progress = new ProgressDialog(EditDetails.this);
                            progress.setMessage("Editing Details...");
                            progress.show();
                            db.collection("customer").document(user_id).update("fName",newName);
                            db.collection("customer").document(user_id).update("phone",newContact);
                            progress.dismiss();
                            Toast.makeText(EditDetails.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(EditDetails.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(EditDetails.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean isValidContact(String s){
        if(s.length()!=10)
            return false;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)<'0'||s.charAt(i)>'9')
                return false;
        }
        return true;
    }

    public boolean isValidName(String s){

        for(int i=0;i<s.length();i++){
            char c =s.charAt(i);
            if(Character.isLetter(c)==false&&c!=' ')
                return false;
        }
        return true;
    }

}
