package com.example.economics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FFR_Carousel_RecylerViewAdapter extends
        RecyclerView.Adapter<FFR_Carousel_RecylerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<FedFundsRateCarouselModel> ffrCarouselModels;

    public FFR_Carousel_RecylerViewAdapter(Context context, ArrayList<FedFundsRateCarouselModel> ffrCarouselModels) {
        this.context = context;
        this.ffrCarouselModels = ffrCarouselModels;
    }

    @NonNull
    @Override
    public FFR_Carousel_RecylerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_ffr_carousel_item, parent, false);
        return new FFR_Carousel_RecylerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FFR_Carousel_RecylerViewAdapter.MyViewHolder holder, int position) {
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
