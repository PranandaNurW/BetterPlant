package com.example.betterplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchPlant extends AppCompatActivity implements MyAdapter.PlantListener {

    EditText txSearch;
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Plant> list;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plant);

        recyclerView = findViewById(R.id.rvSearch);
        database = FirebaseDatabase.getInstance("https://recycleview-29b97-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Plants");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Plant plant = dataSnapshot.getValue(Plant.class);
                    list.add(plant);
                }
                myAdapter = new MyAdapter(SearchPlant.this, list, SearchPlant.this);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txSearch = findViewById(R.id.textSearch);
        txSearch.setMaxWidth(Integer.MAX_VALUE);
        txSearch.setHint("Search Here");

        txSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                myAdapter.getFilter().filter(editable.toString());
            }
        });
    }

    @Override
    public void OnPlantClick(int position) {
        Intent intent = new Intent(this, SensorCahaya.class);
        intent.putExtra("nama", list.get(position).getNama());
        intent.putExtra("cahaya", list.get(position).getCahaya());
        intent.putExtra("deskripsi", list.get(position).getDeskripsi());
        intent.putExtra("provinsi", list.get(position).getProvinsi().toString());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SearchPlant.this,fragment.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}