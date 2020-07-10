package com.example.bsiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MyAccount extends AppCompatActivity {
    private TextView myProfile,myAddress,Settings,Logout;
    private ImageView iProfile,iAddress,iSettings,iLogout;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        setup();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoprofile();
            }
        });
        iProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoprofile();
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        iLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }
    private void setup(){
        myProfile=findViewById(R.id.etProfile);
        myAddress=findViewById(R.id.etAddress);
        Settings=findViewById(R.id.etSettings);
        Logout=findViewById(R.id.etLogout);
        iProfile=findViewById(R.id.imProfile);
        iAddress=findViewById(R.id.imAddress);
        iSettings=findViewById(R.id.imSettings);
        iLogout=findViewById(R.id.imLogout);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    private void logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MyAccount.this,LoginActivity.class));
    }
    private void gotoprofile(){
        startActivity(new Intent(MyAccount.this,ProfileActivity.class));
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
