package com.example.recycleview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class fragmentrec extends Fragment implements MyAdapter.PlantListener {

    TextView lokasi;
    private LocationManager lm;

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Plant> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_plantlist, container, false);

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ;
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    Geocoder geocoder = new Geocoder(view.getContext(), Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    String address = addresses.get(0).getAdminArea();
                    lokasi = view.findViewById(R.id.tvLokasi);
                    lokasi.setText(String.valueOf(address));
                    queryDatabase(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void queryDatabase(String address) {
                recyclerView = view.findViewById(R.id.plantList);
                database = FirebaseDatabase.getInstance("https://recycleview-29b97-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Plants");
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                list = new ArrayList<>();

                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            Plant prov = dataSnapshot.getValue(Plant.class);

                            Iterator provv = prov.getProvinsi().iterator();
                            while(provv.hasNext()){
                                String provi = (String) provv.next();
                                if(provi.equals(address)) {
                                    list.add(prov);
                                } else {
//                                    Toast.makeText(view.getContext(), "Tidak ada tanaman yang cocok di tempatmu", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        myAdapter = new MyAdapter(view.getContext(), list, fragmentrec.this);
                        recyclerView.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onFlushComplete(int requestCode) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        });



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
    }
}