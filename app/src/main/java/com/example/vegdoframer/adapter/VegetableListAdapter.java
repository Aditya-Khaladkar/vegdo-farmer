package com.example.vegdoframer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegdoframer.R;
import com.example.vegdoframer.model.VegetableListModel;

import java.util.List;

public class VegetableListAdapter extends RecyclerView.Adapter<VegetableListAdapter.ViewHolder> {

    private List<VegetableListModel> data;

    public VegetableListAdapter(List<VegetableListModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vegetables_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetableListAdapter.ViewHolder holder, int position) {
        VegetableListModel item = data.get(position);
        holder.txtVegName.setText(item.getVegetableName());
        holder.txtVegPrice.setText(item.getVegetablePrice());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtVegName, txtVegPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtVegName = itemView.findViewById(R.id.txtVegName);
            txtVegPrice = itemView.findViewById(R.id.txtVegPrice);
        }
    }
}
