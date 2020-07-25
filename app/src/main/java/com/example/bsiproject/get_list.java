package com.example.bsiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class get_list extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProviderAdapter adapter;
    private List<Provider> providerList;
    private TextView t1,t2;
    private Button sort;
    private Spinner spn;
    Float rating;
    String item;
    HashMap<String,Float> hashMap=new HashMap<String,Float>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_list);
        setup();
        func();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String service=getIntent().getStringExtra("service");
        t1.setText("List of the Service provider for "+service + " :");
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Service_Provider");
        Query query=ref.child(service);
        query.addListenerForSingleValueEvent(valueEventListener);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item=spn.getSelectedItem().toString();
                if(!item.equals("Select Item")){
                    dosort();
                }
                else{
                    Toast.makeText(get_list.this,"Please Select Item For Sort",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            providerList.clear();
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Provider provider=snapshot.getValue(Provider.class);
                    provider.setRating(hashMap.get(provider.id));
                    providerList.add(provider);
                }
                adapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    private void dosort(){
        Collections.sort(providerList, Provider.RatingComparator);
        adapter.notifyDataSetChanged();
    }

    private void func(){
        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("Provider");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        Provider provider=ds.getValue(Provider.class);
                        hashMap.put(provider.id,provider.rating);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setup(){
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        providerList = new ArrayList<>();
        adapter = new ProviderAdapter(this,providerList);
        recyclerView.setAdapter(adapter);
        t1=findViewById(R.id.txt);
        t2=findViewById(R.id.sort_txt);
        spn=findViewById(R.id.sort_by);
        ArrayAdapter<CharSequence> sort_adapter=ArrayAdapter.createFromResource(this,R.array.sort_by,R.layout.support_simple_spinner_dropdown_item);
        spn.setAdapter(sort_adapter);
        sort=findViewById(R.id.btn_sort);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
