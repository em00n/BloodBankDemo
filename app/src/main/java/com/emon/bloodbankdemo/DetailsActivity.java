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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {
    private TextView bloodgroupTv, dateTv, locationTv,detailTv;
    private Button call,share;
    String number;
    String blood,date,location,detail;
    int REQUEST_CALL=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bloodgroupTv = findViewById(R.id.detailBloodTV);
        dateTv = findViewById(R.id.detailDateTV);
        locationTv = findViewById(R.id.detailLocationTV);
        detailTv = findViewById(R.id.detailTV);

        call = findViewById(R.id.detailCallBTN);
        share = findViewById(R.id.detailShareBTN);


         blood = getIntent().getStringExtra("blood");
         date = getIntent().getStringExtra("date");
         location = getIntent().getStringExtra("location");
         number = getIntent().getStringExtra("number");
         detail = getIntent().getStringExtra("detail");

        bloodgroupTv.setText("Blood Group : "+blood);
        dateTv.setText("Date : "+date);
        locationTv.setText("Location : "+location);
        detailTv.setText("Details : \n"+detail);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Blood Group : "+blood+"\nDate : "+date+"\nLocation : "+location+"\nDetails : \n"+detail);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


    }

    //call
    public void call() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Call to help");
        alert.setCancelable(false);

        alert.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makePhoneCall(number);

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
    private void makePhoneCall(String number) {

        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(DetailsActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DetailsActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(DetailsActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(number);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
