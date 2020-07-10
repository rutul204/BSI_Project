package com.example.bsiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText newUserName,newUserPhoneNumber,newUserAge;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    String name,phoneNumber,age,type,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setup();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DatabaseReference ref1=firebaseDatabase.getReference("User");
        final DatabaseReference databaseReference = ref1.child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                newUserName.setText(userProfile.getUserName());
                newUserPhoneNumber.setText(userProfile.getUserPhoneNumber());
                newUserAge.setText(userProfile.getUserAge());
                type=userProfile.getUserType();
                id=userProfile.getUserId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=newUserName.getText().toString();
                phoneNumber=newUserPhoneNumber.getText().toString();
                age=newUserAge.getText().toString();
                if(validate()){
                    UserProfile userProfile=new UserProfile(age,phoneNumber,name,type,id);
                    databaseReference.setValue(userProfile);
                    finish();
                }
            }
        });
    }
    private Boolean validate(){
        Boolean result=false;
        if(!validateuserName()){
            Toast.makeText(UpdateProfileActivity.this,"Please Enter Name between 4 to 10 characters",Toast.LENGTH_SHORT).show();
        }
        else if(!validateuserPhoneNumber()){
            Toast.makeText(UpdateProfileActivity.this,"Please Enter a Valid PhoneNumber",Toast.LENGTH_SHORT).show();
        }
        else if(!validateuserAge()){
            Toast.makeText(UpdateProfileActivity.this,"Please Enter a Valid Age",Toast.LENGTH_SHORT).show();
        }
        else    result=true;
        return result;
    }
    private Boolean validateuserName(){
        if(name.length()<4 || name.length()>10) return false;
        return true;
    }
    private Boolean validateuserPhoneNumber(){
        if(phoneNumber.length()!=10)    return false;
        return true;
    }
    private Boolean validateuserAge(){
        if(age.isEmpty())   return false;
        return true;
    }
    private void setup(){
        newUserName=findViewById(R.id.ivName);
        newUserPhoneNumber=findViewById(R.id.ivPhoneNumber);
        newUserAge=findViewById(R.id.ivAge);
        save=findViewById(R.id.btnSave);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
