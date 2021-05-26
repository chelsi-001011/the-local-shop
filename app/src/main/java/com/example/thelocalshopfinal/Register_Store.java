package com.example.thelocalshopfinal;

        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.Spinner;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.AdapterView.OnItemSelectedListener;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;

        import org.w3c.dom.Document;

        import java.util.HashMap;
        import java.util.Map;

public class Register_Store extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private TextView tvlatitude, tvlongitude;
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;
    Spinner spinner;
    String[] spinnerValue = {
            "Grocery",
            "Medical",
            "Services",
            "Food"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__store);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        mFullName = findViewById(R.id.name);
        mEmail = findViewById(R.id.emailAddress);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phoneNumber);
        mRegisterBtn = findViewById(R.id.registerButton);
        mLoginBtn = findViewById(R.id.alreadyRegisteredLogin);
        tvlatitude = (TextView)findViewById(R.id.tvlatitude);
        tvlongitude = (TextView)findViewById(R.id.tvlongitude);

        spinner =(Spinner)findViewById(R.id.spinner1);
        spinnerAdapter adapter = new spinnerAdapter(Register_Store.this , android.R.layout.simple_list_item_1);
        adapter.addAll(spinnerValue);
        adapter.add("Choose Category");
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                if(spinner.getSelectedItem() == "Choose Category")
                {

                    //Do nothing.
                }
                else{

                    Toast.makeText(Register_Store.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation(v);
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();
                String phone = mPhone.getText().toString();
                String spin = spinner.getSelectedItem().toString();
                double lat = Double.parseDouble(tvlatitude.getText().toString());
                double longi = Double.parseDouble(tvlongitude.getText().toString());

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("EMAIL IS REQUIRED.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("PASSWORD IS REQUIRED.");
                }

                if (password.length() < 6) {
                    mPassword.setError("PASSWORD MUST BE 6 CHARACTERS ATLEAST.");
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register_Store.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("store").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("StoreName", fullName);
                            user.put("email", email);
                            user.put("phone", phone);
                            user.put("category",spin);
                            user.put("latitude",lat);
                            user.put("longitude",longi);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                    Log.d("TAG","onSuccess: user profile is created for "+ userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), StoreMainActivity.class));
                        } else {
                            Toast.makeText(Register_Store.this, "ERROR! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginStore.class));
            }
        });
    }
    public void getLocation(View view){
        gpsTracker = new GpsTracker(Register_Store.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            tvlatitude.setText(String.valueOf(latitude));
            tvlongitude.setText(String.valueOf(longitude));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }
}
