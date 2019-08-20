package com.emon.bloodbankdemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SearchActivity extends AppCompatActivity {


    Spinner bloodSP;
    private Button searchBtn;
    List<Model> bloodList;
    RecyclerView recyclerView;

    String number = "";
    String callnum = "";
    String blood;
    String[] bloodGroup;
    ArrayAdapter<String> adapter;
    private static final int REQUEST_CALL = 1;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Model> option;
    FirebaseRecyclerAdapter<Model, MyRecyclerViewHolder> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setTitle("Search Donner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchBtn = (Button) findViewById(R.id.search);
        bloodSP = findViewById(R.id.blood);

        bloodGroup = getResources().getStringArray(R.array.blood_array);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, bloodGroup);
        bloodSP.setAdapter(adapter);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BLOOD");
        databaseReference.keepSynced(true);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blood = bloodSP.getSelectedItem().toString();

            query();

            }
        });


    }

    @Override
    protected void onRestart() {
        query();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        if (adapter1 != null)
            adapter1.stopListening();
        super.onStop();
    }

    private void query(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("BLOOD");
        final Query data = reference.orderByChild("blood").equalTo(blood);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        option = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(data, Model.class)
                .build();
        adapter1 = new FirebaseRecyclerAdapter<Model, MyRecyclerViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder,final int position, @NonNull final Model model) {
                holder.nameTV.setText("Name : "+model.getName());
                holder.distTV.setText("Address : "+model.getDisc());
                holder.bloodTV.setText("Blood Group : "+model.getBlood());
                holder.lastDonateTV.setText("Last Donate : "+model.getLastDonate());

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        callnum = model.getMobile();
                        call(position);
                    }


                });
            }

            @NonNull
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_reg, parent, false);
                return new MyRecyclerViewHolder(itemView);
            }
        };
        adapter1.startListening();
        recyclerView.setAdapter(adapter1);
    }
    //call
    public void call(int view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Call Blood Doner");
        alert.setCancelable(false);

        alert.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                number = callnum;
                makePhoneCall();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }


    //phoncall make
    private void makePhoneCall() {

        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(SearchActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SearchActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(SearchActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
