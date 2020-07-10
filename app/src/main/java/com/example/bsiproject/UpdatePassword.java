package com.example.bsiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {
    private Button update;
    private EditText newPassword;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        setup();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=newPassword.getText().toString();
                firebaseUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UpdatePassword.this,"Succesfully Changed Password!!",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(UpdatePassword.this,"Failed Changed Password!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void setup(){
        update=findViewById(R.id.btnUpdatePassword);
        newPassword=findViewById(R.id.etnewPassword);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
