package com.example.vegdoframer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegdoframer.R;
import com.example.vegdoframer.model.HawkerListModel;

import java.util.List;

public class HawkerListAdapter extends RecyclerView.Adapter<HawkerListAdapter.VegetableViewHolder> {
    private List<HawkerListModel> mDataList;

    public HawkerListAdapter(List<HawkerListModel> dataList) {
        this.mDataList = dataList;
    }

    @NonNull
    @Override
    public VegetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.hawker_list, parent, false);
        return new VegetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetableViewHolder holder, int position) {
        HawkerListModel vegetable = mDataList.get(position);
        holder.nameTextView.setText(vegetable.getName());
        holder.priceTextView.setText(vegetable.getPrice());
        holder.locationTextView.setText(vegetable.getHawkerLocation());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class VegetableViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        TextView locationTextView;

        public VegetableViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
        }
    }
}
