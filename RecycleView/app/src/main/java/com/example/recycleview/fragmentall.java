package com.example.recycleview;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragmentall extends Fragment implements MyAdapter.PlantListener {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Plant> list;

    TextView lokasi;
    TextView llokasi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_plantlist, container, false);

        recyclerView = view.findViewById(R.id.plantList);
        database = FirebaseDatabase.getInstance("https://recycleview-29b97-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Plants");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        list = new ArrayList<>();


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Plant plant = dataSnapshot.getValue(Plant.class);
                    list.add(plant);
                }
                myAdapter = new MyAdapter(view.getContext(), list, fragmentall.this);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        lokasi = view.findViewById(R.id.tvLokasi);
        llokasi = view.findViewById(R.id.tvlLokasi);
        lokasi.setVisibility(View.GONE);
        llokasi.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void OnPlantClick(int position) {
        Intent intent = new Intent(getContext(), SensorCahaya.class);
        intent.putExtra("nama", list.get(position).getNama());
        intent.putExtra("cahaya", list.get(position).getCahaya());
        intent.putExtra("deskripsi", list.get(position).getDeskripsi());
        intent.putExtra("provinsi", list.get(position).getProvinsi().toString());
        startActivity(intent);
//        Log.d("Activity", "Onclick berhasil" + position);
    }
}