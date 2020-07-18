package com.example.bsiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class get_list extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProviderAdapter adapter;
    private List<Provider> providerList;
    private TextView t1;
    Float rating;
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
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
