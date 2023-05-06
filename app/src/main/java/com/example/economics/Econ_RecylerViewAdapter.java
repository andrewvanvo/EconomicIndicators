package com.example.economics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Econ_RecylerViewAdapter extends RecyclerView.Adapter<Econ_RecylerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<EconIndicatorModel> econIndicatorModels;
    public Econ_RecylerViewAdapter(Context context, ArrayList<EconIndicatorModel> econIndicatorModels){
        this.context = context;
        this.econIndicatorModels = econIndicatorModels;
    }

    @NonNull
    @Override
    //inflates layout
    public Econ_RecylerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new Econ_RecylerViewAdapter.MyViewHolder(view);
    }

    //assigning values to items
    @Override
    public void onBindViewHolder(@NonNull Econ_RecylerViewAdapter.MyViewHolder holder, int position) {
        holder.textViewName.setText(econIndicatorModels.get(position).getIndicatorName());
        holder.imageView.setImageResource(econIndicatorModels.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, econIndicatorModels.get(holder.getAdapterPosition()).getIndicatorName(),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    //helps with onbinding process
    @Override
    public int getItemCount() {
        return econIndicatorModels.size();
    }

    //similar to oncreate method I suppose
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.econImageView);
            textViewName = itemView.findViewById(R.id.econTextView);

        }
    }
}
