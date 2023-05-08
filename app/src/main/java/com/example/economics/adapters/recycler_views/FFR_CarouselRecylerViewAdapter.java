package com.example.economics.adapters.recycler_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economics.models.Econ_Carousel_Model;
import com.example.economics.R;

import java.util.ArrayList;

public class FFR_CarouselRecylerViewAdapter extends RecyclerView.Adapter<FFR_CarouselRecylerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Econ_Carousel_Model> ffrCarouselModels;

    public FFR_CarouselRecylerViewAdapter(Context context, ArrayList<Econ_Carousel_Model> ffrCarouselModels) {
        this.context = context;
        this.ffrCarouselModels = ffrCarouselModels;
    }

    @NonNull
    @Override
    public FFR_CarouselRecylerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_ffr_carousel_item, parent, false);
        return new FFR_CarouselRecylerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FFR_CarouselRecylerViewAdapter.MyViewHolder holder, int position) {
        holder.statView.setText(ffrCarouselModels.get(position).getStat());
        holder.descriptorView.setText(ffrCarouselModels.get(position).getDescriptor());
    }

    @Override
    public int getItemCount() {
        return ffrCarouselModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView statView;
        TextView descriptorView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            statView = itemView.findViewById(R.id.ffrStatView);
            descriptorView = itemView.findViewById(R.id.ffrStatDescriptorView);
        }
    }

}
