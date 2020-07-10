package com.example.bsiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profilePic;
    private TextView profileName,profileAge,profilePhoneNumber;
    private Button editProfile,changePassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setup();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(firebaseAuth.getUid()!=null) {
            DatabaseReference ref1=firebaseDatabase.getReference("User");
            DatabaseReference databaseReference = ref1.child(firebaseAuth.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    profileName.setText("Name : " + userProfile.getUserName());
                    profilePhoneNumber.setText("PhoneNumber : " + userProfile.getUserPhoneNumber());
                    profileAge.setText("Age : " + userProfile.getUserAge());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        }
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,UpdateProfileActivity.class));
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,UpdatePassword.class));
            }
        });
    }
    private void setup(){
        profilePic=findViewById(R.id.etProfilePic);
        profileName=findViewById(R.id.etProfileName);
        profileAge=findViewById(R.id.etProfileAge);
        profilePhoneNumber=findViewById(R.id.etProfilePhoneNumber);
        editProfile=findViewById(R.id.btnProfileUpdate);
        changePassword=findViewById(R.id.btnChangePassword);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
