package com.emon.bloodbankdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String number,details,currentuid,selectedkey;
//firebase

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseRecyclerOptions<Post> option;
    FirebaseRecyclerAdapter<Post,PostRecyclerViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        getSupportActionBar().setTitle("Blood Needed");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recyclerview
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //firebase
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("POST");
        mAuth = FirebaseAuth.getInstance();
        currentuid = mAuth.getCurrentUser().getUid();

        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        showData();

    }
    protected void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showData();
    }
    //show data
    private void showData() {
        option= new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(databaseReference, Post.class)
                .build();
        adapter= new FirebaseRecyclerAdapter<Post, PostRecyclerViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull PostRecyclerViewHolder holder,final int position, @NonNull final Post model) {
                holder.showBloodTV.setText("Blood Group : "+model.getBloodgroup());
                holder.showDateTV.setText("Date : "+model.getDate());
                holder.showLocationTV.setText("Address : "+model.getLocation()+"...");
                number=model.getNumber();
                details=model.getDetails();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentuid.equals(model.getUid())){
                            selectedkey = getSnapshots().getSnapshot(position).getKey();
                            Intent intent=new Intent(ShowActivity.this,UpDeActivity.class);
                            intent.putExtra("blood",model.getBloodgroup());
                            intent.putExtra("date",model.getDate());
                            intent.putExtra("location",model.getLocation());
                            intent.putExtra("number",model.getNumber());
                            intent.putExtra("detail",model.getDetails());
                            intent.putExtra("selectedkey",selectedkey);
                            startActivity(intent);
                        }
                        else if(!currentuid.equals(model.getUid())) {
                            Intent intent=new Intent(ShowActivity.this,DetailsActivity.class);
                            intent.putExtra("blood",model.getBloodgroup());
                            intent.putExtra("date",model.getDate());
                            intent.putExtra("location",model.getLocation());
                            intent.putExtra("number",model.getNumber());
                            intent.putExtra("detail",model.getDetails());
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(ShowActivity.this, "something error", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            @NonNull
            @Override
            public PostRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView= LayoutInflater.from(getBaseContext()).inflate(R.layout.item_show,parent,false);
                return new PostRecyclerViewHolder(itemView);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    public void addbutton(View view) {
        startActivity(new Intent(ShowActivity.this,PostActivity.class));
    }
}
