package com.example.bsiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class Service_Provider extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__provider);
        setup();

    }
    private void setup(){
        firebaseAuth= FirebaseAuth.getInstance();
    }
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Service_Provider.this,LoginActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
            }
            case R.id.profileMenu:{
                startActivity(new Intent(Service_Provider.this,ProfileActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
