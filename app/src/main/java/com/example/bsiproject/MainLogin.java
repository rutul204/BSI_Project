package com.example.bsiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainLogin extends AppCompatActivity {

    private Button phonenumber,emailid;
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        setup();

        phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainLogin.this,LoginViaOTP.class));
            }
        });

        emailid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainLogin.this,LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainLogin.this,RegisterActivity.class));
            }
        });
    }

    private void setup(){
        phonenumber=findViewById(R.id.btnMainLogin_PhoneNumber);
        emailid=findViewById(R.id.btnMainLogin_EmailId);
        register=findViewById(R.id.tvMainLogin_gotoRegister);
    }
}
