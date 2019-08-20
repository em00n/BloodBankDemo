package com.emon.bloodbankdemo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class UpDeActivity extends AppCompatActivity {
    private EditText bloodgroupET, dateET, locationET, numberET,detailET;
    private Button updateBTN,deleteBTN;
    private FirebaseAuth mAuth;
    String useruid;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_de);

        bloodgroupET = findViewById(R.id.updeBloodET);
        dateET = findViewById(R.id.updeDateET);
        locationET = findViewById(R.id.updeLocationET);
        numberET = findViewById(R.id.updeNumberET);
        detailET = findViewById(R.id.updeDetailsET);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateBTN = findViewById(R.id.updateBTN);
        deleteBTN = findViewById(R.id.deleteBTN);

       useruid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        final String selectedkey = getIntent().getStringExtra("selectedkey");
        String blood = getIntent().getStringExtra("blood");
        String date = getIntent().getStringExtra("date");
        String location = getIntent().getStringExtra("location");
        String number = getIntent().getStringExtra("number");
        String detail = getIntent().getStringExtra("detail");

        bloodgroupET.setText(blood);
        dateET.setText(date);
        locationET.setText(location);
        numberET.setText(number);
        detailET.setText(detail);


        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("POST");

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference
                        .child(selectedkey)
                        .setValue(new Post(bloodgroupET.getText().toString(), dateET.getText().toString(), locationET.getText().toString(),numberET.getText().toString(),detailET.getText().toString(),useruid))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpDeActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpDeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference
                        .child(selectedkey)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpDeActivity.this, "delete", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpDeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
