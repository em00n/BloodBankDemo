package com.emon.bloodbankdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEmailEdit, mPasswordEdit, nameET, mobileET,lastDonateET;
    Spinner distSP, bloodSP;
    private Button mRegister;
    private Button mLogin;
    private TextView gotoLogin;
    private FirebaseAuth mAuth;

    String dist, blood, name, mobile,lastDonate,uid;
    String []bloodGroup;
    String []distGroup;
    ArrayAdapter<String>adapter;
    ArrayAdapter<String>adapter2;
    ProgressDialog pd;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailEdit = (EditText) findViewById(R.id.email);
        mPasswordEdit = (EditText) findViewById(R.id.password);
        nameET = findViewById(R.id.name);
        mobileET = findViewById(R.id.number);
        distSP = findViewById(R.id.dist);
        bloodSP = findViewById(R.id.blood);
        lastDonateET = findViewById(R.id.lastdonet);



        bloodGroup=getResources().getStringArray(R.array.blood_array);
        adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,bloodGroup);
        bloodSP.setAdapter(adapter);

        distGroup=getResources().getStringArray(R.array.dist_array);
        adapter2=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,distGroup);
        distSP.setAdapter(adapter2);




        mRegister = (Button) findViewById(R.id.register);
        mLogin = (Button) findViewById(R.id.login_btn);



        mAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd=new ProgressDialog(RegisterActivity.this);
                pd.setTitle("Please wait");
                pd.setMessage("Loading....");
                pd.show();
                name = nameET.getText().toString().trim();
                mobile = mobileET.getText().toString().trim();
                dist = distSP.getSelectedItem().toString();
                blood = bloodSP.getSelectedItem().toString();
                lastDonate=lastDonateET.getText().toString();
                String email = mEmailEdit.getText().toString().trim();
                String password = mPasswordEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(dist) && !TextUtils.isEmpty(blood)&&!TextUtils.isEmpty(lastDonate)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("blood",blood);
                                intent.putExtra("mobile",mobile);
                                intent.putExtra("dist",dist);
                                intent.putExtra("name",name);
                                intent.putExtra("lastDonate",lastDonate);
                                startActivity(intent);
                                finish();
                                pd.dismiss();

                            } else {
                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

    }



    public void forget(View view) {
        startActivity(new Intent(RegisterActivity.this,ForgetPassActivity.class));
    }
}
