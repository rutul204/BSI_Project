package com.example.bsiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Electrical extends AppCompatActivity {
    private TextView Txt1,Txt2,Txt3,Txt4,Txt5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrical);
        setup();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Electrical.this,get_list.class);
                intent.putExtra("service",Txt1.getText().toString());
                startActivity(intent);
            }
        });
    }
    private void setup(){
        Txt1=findViewById(R.id.txt1);
        Txt2=findViewById(R.id.txt2);
        Txt3=findViewById(R.id.txt3);
        Txt4=findViewById(R.id.txt4);
        Txt5=findViewById(R.id.txt5);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
