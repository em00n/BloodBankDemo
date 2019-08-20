package com.emon.bloodbankdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView nameTv, numberTv, addressTv, lastDonateTv;
    Button updateBTN;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String uid, selectedkey;
    String name, number, address, lastDonate,bloodgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameTv = findViewById(R.id.proNameTv);
        numberTv = findViewById(R.id.proNumberTv);
        addressTv = findViewById(R.id.proLocationTv);
        lastDonateTv = findViewById(R.id.proLastdonateTv);
        updateBTN = findViewById(R.id.proUpdateBTN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getProfile();

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,ProfileUpdateActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("number",number);
                intent.putExtra("address",address);
                intent.putExtra("lastdonate",lastDonate);
                intent.putExtra("selectedkey",selectedkey);
                intent.putExtra("bloodgroup",bloodgroup);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onRestart() {
        getProfile();
        super.onRestart();
    }

    @Override
    protected void onStart() {
        getProfile();
        super.onStart();
    }

    void getProfile(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BLOOD");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("BLOOD");
        databaseReference.keepSynced(true);
        final Query data = reference.orderByChild("uid").equalTo(uid);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Model model = snapshot.getValue(Model.class);
                    name = model.getName();
                    number = model.getMobile();
                    address = model.getDisc();
                    lastDonate = model.getLastDonate();
                    nameTv.setText("Name : " + name);
                    numberTv.setText("Number : " + number);
                    addressTv.setText("Address : " + address);
                    lastDonateTv.setText("Last Donate : " + lastDonate);
                    selectedkey = snapshot.getKey();
                    bloodgroup=model.getBlood();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
