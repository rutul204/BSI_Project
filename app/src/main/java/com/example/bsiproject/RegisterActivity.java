package com.example.bsiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText userName,userPassword,userEmail,userAge,userPhoneNumber;
    private Button register;
    private Spinner userType;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ImageView userProfile;
    String email,password,age,name,phoneNumber,type,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setup();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                             //sendEmailVerifiaction();
                            sendUserData();
                            Toast.makeText(RegisterActivity.this,"Successfully Registered and data Uploaded, Verification E-Mail has been sent",Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                }
                else{
                    System.out.println("error");
                }
            }
        });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }
    private void setup(){
        userName = findViewById(R.id.etUserName);
        userEmail = findViewById(R.id.etEmail);
        userPassword = findViewById(R.id.etPassword);
        register = findViewById(R.id.btnLogout);
        userLogin = findViewById(R.id.etLogin);
        userAge =findViewById(R.id.etAge);
        userProfile= findViewById(R.id.imProfile);
        userPhoneNumber=findViewById(R.id.etPhoneNumber);
        userType=findViewById(R.id.etUserType);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.UserType,R.layout.support_simple_spinner_dropdown_item);
        userType.setAdapter(adapter);
    }
    private Boolean validate(){
        boolean result=false;
        email = userEmail.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        name=userName.getText().toString().trim();
        age= userAge.getText().toString().trim();
        phoneNumber=userPhoneNumber.getText().toString().trim();
        type=userType.getSelectedItem().toString();

        if(!validateName()){
            Toast.makeText(this,"Please Enter Name between 4 to 10 characters",Toast.LENGTH_SHORT).show();
        }
        else if(!validateEmail()){
            Toast.makeText(this,"Please Enter Valid Emial-Address",Toast.LENGTH_SHORT).show();
        }
        else if(!validatePassword()){
            Toast.makeText(this,"Please Enter Password between 4 to 15 characters",Toast.LENGTH_SHORT).show();
        }
        else if(!validateAge()){
            Toast.makeText(this,"Please Enter Age",Toast.LENGTH_SHORT).show();
        }
        else if(!validatePhoneNumer()){
            Toast.makeText(RegisterActivity.this,"Please Enter a valid Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(!validateuserType()){
            Toast.makeText(this,"Please Select a Usertype",Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }
    private Boolean validatePhoneNumer(){
        if(phoneNumber.length()==0) return false;
        return true;
    }
    private Boolean validateName(){
        if((name.length() < 4) || (name.length() > 10)) {
            return false;
        }
        return true;
    }
    private Boolean validateEmail(){
        if(email.isEmpty()) return false;
        return true;
    }
    private Boolean validatePassword(){
        if((password.length() < 4) || (password.length() > 15)) {
            return false;
        }
        return true;
    }
    private Boolean validateuserType(){
        if(userType.getSelectedItem()==null)    return false;
        String usertype=userType.getSelectedItem().toString();
        if(usertype.equals("Select User Type")) return false;
        return true;
    }
    private Boolean validateAge(){
        if(age.isEmpty()) {
            return false;
        }
        return true;
    }
    private void sendEmailVerifiaction(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(RegisterActivity.this,"Successfully Registered and data Uploaded, Verification E-Mail has been sent",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Verification E-Mail hasn't been sent, Try Again",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserData(){
          DatabaseReference myref = FirebaseDatabase.getInstance().getReference("User");
          String id=firebaseAuth.getUid();
          UserProfile userProfile=new UserProfile(age,phoneNumber,name,type,id);
          myref.child(id).setValue(userProfile);
          if(type.equals("Service_Provider")){
              DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("Provider");
              Float rating = 5.0f;
              Provider provider=new Provider(age,phoneNumber,name,id,rating);
              ref1.child(id).setValue(provider);
          }
    }
}
