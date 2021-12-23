package com.example.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<Plant> list;
    ArrayList<Plant> listFull;
//    ArrayList<Plant> list;
    private PlantListener mplantListener;

    public MyAdapter(Context context, ArrayList<Plant> list, PlantListener plantListener) {
        this.context = context;
        this.listFull = list;
//        this.list = list;
        this.mplantListener = plantListener;
        this.list = new ArrayList<>(listFull);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_fragmentall, parent, false);
        return new MyViewHolder(v, mplantListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Plant plant = list.get(position);
        holder.nama.setText(plant.getNama());
        String caha = plant.getCahaya();
        switch (caha) {
            case "1":
                holder.cahaya.setText("Sangat Redup");
                break;
            case "2":
                holder.cahaya.setText("Redup");
                break;
            case "3":
                holder.cahaya.setText("Normal");
                break;
            case "4":
                holder.cahaya.setText("Cerah");
                break;
            case "5":
                holder.cahaya.setText("Sangat Cerah");
                break;
            default:
                holder.cahaya.setText("Silau Men!");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return plantFilter;
    }

    private final Filter plantFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Plant> filteredPlantList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredPlantList.addAll(listFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
//                switch (filterPattern) {
//                    case "Sangat Redup":
//                        filterPattern = "1";
//                        break;
//                    case "Redup":
//                        filterPattern = "2";
//                        break;
//                    case "Normal":
//                        filterPattern = "3";
//                        break;
//                    case "Cerah":
//                        filterPattern = "4";
//                        break;
//                    case "Sangat Cerah":
//                        filterPattern = "5";
//                        break;
//                    default:
//                        filterPattern = charSequence.toString().toLowerCase().trim();
//                        break;
//                }

                for (Plant plant : listFull){
                    if(plant.nama.toLowerCase().contains(filterPattern))
                        filteredPlantList.add(plant);
                    else if(plant.cahaya.toLowerCase().contains(filterPattern)){
                        filteredPlantList.add(plant);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredPlantList;
            results.count = filteredPlantList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nama, cahaya;
        PlantListener nplantListener;

        public MyViewHolder(@NonNull View itemView, PlantListener plantListener) {
            super(itemView);

            nama = itemView.findViewById(R.id.tvName);
            cahaya = itemView.findViewById(R.id.tvCahaya);
            this.nplantListener = plantListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            nplantListener.OnPlantClick(getAdapterPosition());
        }
    }

    public interface PlantListener {
        void OnPlantClick(int position);
    }

}
