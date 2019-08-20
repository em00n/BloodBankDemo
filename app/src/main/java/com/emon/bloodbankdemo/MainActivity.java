package com.emon.bloodbankdemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CardView donarList, searchDoner, post, rules;
    ViewFlipper viewFlipper;
    String dist, blood, name, mobile, lastDonate, uid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        donarList = findViewById(R.id.donerList);
        searchDoner = findViewById(R.id.searchDonner);
        post = findViewById(R.id.post);
        rules = findViewById(R.id.rules);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("BLOOD");


        name = getIntent().getStringExtra("name");
        blood = getIntent().getStringExtra("blood");
        dist = getIntent().getStringExtra("dist");
        mobile = getIntent().getStringExtra("mobile");
        lastDonate = getIntent().getStringExtra("lastDonate");
        viewFlipper = findViewById(R.id.slideVF);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(dist) && !TextUtils.isEmpty(blood) && !TextUtils.isEmpty(lastDonate)) {
            addData();
        }


        //image sliding
        int[] images = {R.drawable.four, R.drawable.one, R.drawable.two, R.drawable.three,};
        for (int image : images) {
            flipperImage(image);
        }


        donarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BloodgrouplistActivity.class));
            }
        });
        searchDoner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));

            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowActivity.class));
            }
        });
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RulesActivity.class));
            }
        });




    }


    private void addData() {

        Model model = new Model(name, mobile, dist, blood, lastDonate, uid);
        databaseReference.push()
                .setValue(model);
    }

    //this methode use in slideing
    public void flipperImage(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000); // 2 sec
        viewFlipper.setAutoStart(true);
        //animation
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);

    }

    //query data
//    void query() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("BLOOD");
//        databaseReference.keepSynced(true);
//        final Query data = reference.orderByChild("uid").equalTo(uid);
//        data.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Model model = snapshot.getValue(Model.class);
//                    cname = model.getName();
//                    clastdonate = model.getLastDonate();
//                    cnameTv.setText("Name : " + name);
//                    clastdonateTv.setText("Last Donate : " + lastDonate);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));

        } else if (id == R.id.nav_donerlist) {
            startActivity(new Intent(MainActivity.this, BloodgrouplistActivity.class));


        } else if (id == R.id.nav_searchdonner) {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));

        } else if (id == R.id.nav_bloodneed) {
            startActivity(new Intent(MainActivity.this, ShowActivity.class));


        } else if (id == R.id.nav_rules) {
            startActivity(new Intent(MainActivity.this, RulesActivity.class));


        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_send) {
            Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
            feedbackEmail.setType("text/email");
            feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {"emonhassan042@gmail.com"});
            feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));
        }else if (id==R.id.nav_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Share.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
