package com.emon.bloodbankdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileUpdateActivity extends AppCompatActivity {

    private EditText lastdonateET, addressET, numberET, nameET;
    private Button updateBTN;
    private FirebaseAuth mAuth;
    String useruid, bloodgroup;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameET = findViewById(R.id.upproNameET);
        numberET = findViewById(R.id.upproNumberET);
        addressET = findViewById(R.id.upproAddressET);
        lastdonateET = findViewById(R.id.upproLastdonateET);

        updateBTN = findViewById(R.id.updateProBTN);

        final String selectedkey = getIntent().getStringExtra("selectedkey");
        String name = getIntent().getStringExtra("name");
        String number = getIntent().getStringExtra("number");
        String address = getIntent().getStringExtra("address");
        String lastdonate = getIntent().getStringExtra("lastdonate");
        bloodgroup = getIntent().getStringExtra("bloodgroup");

        nameET.setText(name);
        numberET.setText(number);
        addressET.setText(address);
        lastdonateET.setText(lastdonate);

        useruid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BLOOD");

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference
                        .child(selectedkey)
                        .setValue(new Model(nameET.getText().toString(), numberET.getText().toString(), addressET.getText().toString(), bloodgroup, lastdonateET.getText().toString(), useruid))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfileUpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileUpdateActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
