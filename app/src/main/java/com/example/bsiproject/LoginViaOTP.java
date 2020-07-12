package com.example.bsiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginViaOTP extends AppCompatActivity {

    private EditText phonenumber,enteredotp;
    private Button genotp,login;
    private TextView register;
    private String verificationotp;
    private PhoneAuthProvider.ForceResendingToken token;
    private boolean check=false;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_via_o_t_p);
        //set up views
        setup();
        genotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String p = phonenumber.getText().toString().trim();
                    Toast.makeText(LoginViaOTP.this, "OTP sending...", Toast.LENGTH_SHORT).show();
                    requestOTP(p);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check) {
                    String otp = enteredotp.getText().toString();
                    if (otp.isEmpty() || otp.length() != 6) {
                        Toast.makeText(LoginViaOTP.this, "Entered Valid OTP", Toast.LENGTH_SHORT).show();
                    } else {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationotp, otp);
                        verifyfirebaseAuth(credential);
                    }
                }
                else{
                    Toast.makeText(LoginViaOTP.this,"Generate OTP",Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginViaOTP.this,RegisterActivity.class));
            }
        });
    }

    private void verifyfirebaseAuth(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginViaOTP.this,"SignIN Successful...",Toast.LENGTH_SHORT).show();
                    checkusertype();
                }
                else{
                    Toast.makeText(LoginViaOTP.this,"SignIN Failed...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setup(){
        phonenumber=findViewById(R.id.etLoginViaOTP_PhoneNumber);
        enteredotp=findViewById(R.id.etLoginViaOTP_OTP);
        genotp=findViewById(R.id.btnLoginViaOTP_genOTP);
        login=findViewById(R.id.btnLoginViaOTP_SignIn);
        register=findViewById(R.id.tvLoginViaOTP_gotoRegister);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    private boolean validate(){
        String pn=phonenumber.getText().toString().trim();
        if(pn.isEmpty() || pn.length()!=10){
            Toast.makeText(LoginViaOTP.this,"Enter Valid PhoneNumber",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void requestOTP(String p){
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+p, 60, TimeUnit.SECONDS, LoginViaOTP.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(LoginViaOTP.this,"OTP sent...",Toast.LENGTH_SHORT).show();
                verificationotp=s;
                token=forceResendingToken;
                check=true;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(LoginViaOTP.this,"Verifivation Successful",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(LoginViaOTP.this,"Cannot SignIn"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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
                        startActivity(new Intent(LoginViaOTP.this,Customer.class));
                    }
                    if(type.equals("Service_Provider")){
                        startActivity(new Intent(LoginViaOTP.this,Service_Provider.class));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginViaOTP.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
