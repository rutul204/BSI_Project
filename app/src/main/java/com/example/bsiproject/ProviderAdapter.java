package com.example.bsiproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder> {

    private Context mCtx;
    private List<Provider> ProviderList;

    public ProviderAdapter(Context mCtx, List<Provider> ProviderList) {
        this.mCtx = mCtx;
        this.ProviderList = ProviderList;
    }

    @NonNull
    @Override
    public ProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_provider, parent, false);
        return new ProviderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderViewHolder holder, int position) {
        final Provider Provider = ProviderList.get(position);
        holder.textViewName.setText("Name : " + Provider.name);
        holder.textViewAge.setText("Age : " + Provider.age);
        holder.textViewNumber.setText("PhoneNumber : " + Provider.phoneNumber);
        holder.textViewRating.setText("Rating : ");
        holder.rating.setRating(Provider.rating);
        holder.myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mCtx,PlaceOrder.class);
                intent.putExtra("name",Provider.name);
                intent.putExtra("number",Provider.phoneNumber);
                intent.putExtra("age",Provider.age);
                intent.putExtra("rating",Provider.rating);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProviderList.size();
    }

    class ProviderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewAge, textViewNumber,textViewRating;
        RatingBar rating;
        ConstraintLayout myLayout;
        public ProviderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            textViewAge = itemView.findViewById(R.id.age);
            textViewNumber = itemView.findViewById(R.id.number);
            textViewRating = itemView.findViewById(R.id.rating_txt);
            rating = itemView.findViewById(R.id.ratingBar);
            myLayout = itemView.findViewById(R.id.conLayout);
        }
    }
}