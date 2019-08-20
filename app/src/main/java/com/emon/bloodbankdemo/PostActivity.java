package com.emon.bloodbankdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {
    private EditText bloodgroupET, dateET, locationET, numberET,detailET;
    private Button postBTN;
    private FirebaseAuth mAuth;
    ProgressDialog pd;
    String useruid;

    String bloodgroup, date, location, number,detail;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bloodgroupET = findViewById(R.id.postBloodET);
        dateET = findViewById(R.id.postDateET);
        locationET = findViewById(R.id.postLocationET);
        numberET = findViewById(R.id.postNumberET);
        detailET = findViewById(R.id.postDetailsET);

        postBTN = findViewById(R.id.postBTN);
        FirebaseApp.initializeApp(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("POST");

        mAuth = FirebaseAuth.getInstance();

        useruid = mAuth.getCurrentUser().getUid();

        postBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();


            }
        });
    }
    private void addData() {

        bloodgroup = bloodgroupET.getText().toString();
        date = dateET.getText().toString();
        location = locationET.getText().toString();
        number = numberET.getText().toString();
        detail = detailET.getText().toString();
        if (!bloodgroup.isEmpty() && !date.isEmpty() && !number.isEmpty()) {
            pd = new ProgressDialog(PostActivity.this);
            pd.setTitle("Please wait");
            pd.setMessage("Loading....");
            pd.show();
            Post post = new Post(bloodgroup, date, location, number,detail,useruid);
            databaseReference.push()
                    .setValue(post);
          //  startActivity(new Intent(PostActivity.this,ShowActivity.class));
            finish();
        }
    }

    public void clear() {

    }
}
