package com.example.bsiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Register;
    private TextView forgotPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            finish();
            startActivity(new Intent(LoginActivity.this, Customer.class));
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,PasswordActivity.class));
            }
        });
    }
    private void setup() {
        Name = (EditText)findViewById(R.id.etUserName);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogout);
        Register =(TextView)findViewById(R.id.etRegister);
        forgotPassword = (TextView)findViewById(R.id.etForgotPassword);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
    }
    private void validate(String userEmail,String userPassword){
        if(userEmail.isEmpty()){
            Toast.makeText(this,"Enter Email-Id",Toast.LENGTH_SHORT).show();
        }
        else if(userPassword.isEmpty()){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else {
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkEmailVerification();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        boolean emailflag=firebaseUser.isEmailVerified();
        //if(emailflag){
            finish();
            checkusertype();
            //startActivity(new Intent(this, Customer.class));
        /*}
        else{
            Toast.makeText(this,"Verify Your Email-Address!!",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }*/
    }
    private void checkusertype(){
        if(firebaseAuth.getUid()!=null){
            DatabaseReference ref1=firebaseDatabase.getReference("User");
            System.out.println(firebaseAuth.getUid());
            DatabaseReference databaseReference=ref1.child(firebaseAuth.getUid());
//            DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
                    String type=userProfile.getUserType();
                    if(type.equals("Customer")){
                        startActivity(new Intent(LoginActivity.this,Customer.class));
                    }
                    if(type.equals("Service_Provider")){
                        startActivity(new Intent(LoginActivity.this,Service_Provider.class));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
