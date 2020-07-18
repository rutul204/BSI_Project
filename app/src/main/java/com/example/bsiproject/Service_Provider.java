package com.example.bsiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Service_Provider extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Spinner spn;
    private Button btn;
    Float rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__provider);
        setup();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }
    private void setup(){
        firebaseAuth= FirebaseAuth.getInstance();
        spn=findViewById(R.id.skill);
        btn=findViewById(R.id.add_skill);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.skill,R.layout.support_simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
    }
    private void check(){
        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("Service_Provider");
        DatabaseReference ref2=ref1.child(spn.getSelectedItem().toString());
        System.out.println(firebaseAuth.getUid());
        ref2.orderByChild("id").equalTo(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())  addData();
                else{
                    Toast.makeText(Service_Provider.this,spn.getSelectedItem().toString()+" is already added",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void addData(){
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("User");
        DatabaseReference ref2=ref1.child(firebaseAuth.getUid());
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile profile=dataSnapshot.getValue(UserProfile.class);
                String name=profile.getUserName();
                String age=profile.getUserAge();
                String id=profile.getUserId();
                String number=profile.getUserPhoneNumber();
                DatabaseReference ref3=FirebaseDatabase.getInstance().getReference("Service_Provider");
                DatabaseReference ref4=ref3.child(spn.getSelectedItem().toString());
                Provider provider=new Provider(age,number,name,id);
                ref4.child(id).setValue(provider);
                Toast.makeText(Service_Provider.this,spn.getSelectedItem().toString()+" added Successfully",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Service_Provider.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
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
