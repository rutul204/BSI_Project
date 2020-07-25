package com.example.bsiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class PlaceOrder extends AppCompatActivity {

    TextView name,number,age;
    RatingBar ratingBar;
    String sname,snumber,sage;
    Button btn;
    Float rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        setup();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Data();
    }
    private void Data(){
        sname=getIntent().getStringExtra("name");
        snumber=getIntent().getStringExtra("number");
        sage=getIntent().getStringExtra("age");
        rating=getIntent().getFloatExtra("rating",0);
        name.setText("Name : " + sname);
        number.setText("PhoneNumber : " + snumber);
        age.setText("Age : " + sage);
        ratingBar.setRating(rating);
    }
    private void setup(){
        name=findViewById(R.id.pname);
        number=findViewById(R.id.pnumber);
        age=findViewById(R.id.page);
        ratingBar=findViewById(R.id.prating);
        btn=findViewById(R.id.place_order);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
