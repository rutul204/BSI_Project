package com.example.bsiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Customer extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private BottomNavigationView bottomNavigationView;
    private ImageView Carpentry,Electrical,Plumbing,Cleaning,Home_appliances,Painting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setup();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(Customer.this,"You are Already on the Home Page",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_cart:
                        startActivity(new Intent(Customer.this,Orders.class));
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(Customer.this,MyAccount.class));
                        break;
                }
                return true;
            }
        });
        Carpentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Customer.this,Carpentry.class));
            }
        });
        Electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Customer.this,Electrical.class));
            }
        });
        Plumbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Customer.this,Plumbing.class));
            }
        });
        Home_appliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Customer.this,Home_appliances.class));
            }
        });
        Cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Customer.this,Cleaning.class));
            }
        });
        Painting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Customer.this,Painting.class));
            }
        });
    }
    private void setup(){
        firebaseAuth=FirebaseAuth.getInstance();
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        Carpentry=findViewById(R.id.carpentry);
        Plumbing=findViewById(R.id.plumbing);
        Electrical=findViewById(R.id.electrical);
        Painting=findViewById(R.id.painting);
        Cleaning=findViewById(R.id.cleaning);
        Home_appliances=findViewById(R.id.home_appliances);
    }
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Customer.this,LoginActivity.class));
    }

}
