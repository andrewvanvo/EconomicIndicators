package com.example.economics;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economics.activities.DurableActivity;
import com.example.economics.activities.FedFundsRateActivity;
import com.example.economics.activities.GdpActivity;
import com.example.economics.activities.InflationActivity;
import com.example.economics.activities.TreasuryYieldsActivity;
import com.example.economics.activities.UnemploymentActivity;

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
                String indicator = econIndicatorModels.get(holder.getAdapterPosition()).getIndicatorName();
                Intent intent = null;

                switch(indicator){
                    case "Federal Funds Rate":
                        intent = new Intent(view.getContext(), FedFundsRateActivity.class);
                        break;
                    case "GDP":
                        intent = new Intent(view.getContext(), GdpActivity.class);
                        break;
                    case "Inflation":
                        intent = new Intent(view.getContext(), InflationActivity.class);
                        break;
                    case "Treasury Yields":
                        intent = new Intent(view.getContext(), TreasuryYieldsActivity.class);
                        break;
                    case "Unemployment Rate":
                        intent = new Intent(view.getContext(), UnemploymentActivity.class);
                        break;
                    case "Durable Goods Orders":
                        intent = new Intent(view.getContext(), DurableActivity.class);
                        break;
                    default:
                        intent = new Intent(view.getContext(), MainActivity.class);
                        break;
                }
                view.getContext().startActivity(intent);

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
