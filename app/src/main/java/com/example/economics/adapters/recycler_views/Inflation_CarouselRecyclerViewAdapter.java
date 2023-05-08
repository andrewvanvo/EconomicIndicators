package com.example.economics.adapters.recycler_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economics.R;
import com.example.economics.models.Econ_Carousel_Model;

import java.util.ArrayList;

public class Inflation_CarouselRecyclerViewAdapter extends RecyclerView.Adapter<Inflation_CarouselRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Econ_Carousel_Model> carouselModels;

    public Inflation_CarouselRecyclerViewAdapter(Context context, ArrayList<Econ_Carousel_Model> carouselModels) {
        this.context = context;
        this.carouselModels = carouselModels;
    }

    @NonNull
    @Override
    public Inflation_CarouselRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_cpi_carousel_item, parent, false);
        return new Inflation_CarouselRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Inflation_CarouselRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.statView.setText(carouselModels.get(position).getStat());
        holder.descriptorView.setText(carouselModels.get(position).getDescriptor());
    }

    @Override
    public int getItemCount() {
        return carouselModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView statView;
        TextView descriptorView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            statView = itemView.findViewById(R.id.cpiStatView);
            descriptorView = itemView.findViewById(R.id.cpiStatDescriptorView);
        }
    }

}
