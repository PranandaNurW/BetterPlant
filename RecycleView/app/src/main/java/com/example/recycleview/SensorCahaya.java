package com.example.recycleview;

import android.annotation.SuppressLint;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SensorCahaya extends AppCompatActivity implements SensorEventListener {
    TextView tvKategori, tvCahaya, nama, cahaya, deskripsi, lokasi;
    ImageView img;
    SensorManager sensorManager;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        tvKategori = (TextView) findViewById(R.id.tvKategori);
        tvCahaya = (TextView) findViewById(R.id.tvCahaya);
        img = findViewById(R.id.imageView);

        nama = findViewById(R.id.tvNama);
        cahaya = findViewById(R.id.tvC);
        deskripsi = findViewById(R.id.tvDeskripsi);
        lokasi = findViewById(R.id.tvLokasi);

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //mengambil parsing data
        nama.setText(getIntent().getStringExtra("nama"));
        String caha = getIntent().getStringExtra("cahaya");
        switch (caha) {
            case "1":
                cahaya.setText("Sangat Redup");
                break;
            case "2":
                cahaya.setText("Redup");
                break;
            case "3":
                cahaya.setText("Normal");
                break;
            case "4":
                cahaya.setText("Cerah");
                break;
            case "5":
                cahaya.setText("Sangat Cerah");
                break;
            default:
                cahaya.setText("Silau Men!");
                break;
        }

        deskripsi.setText(getIntent().getStringExtra("deskripsi"));
        String loc = getIntent().getStringExtra("provinsi");
        lokasi.setText(loc.substring(1, loc.length() - 1));
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            if (sensorEvent.values[0] >= 0 && sensorEvent.values[0] <40.0) {
                tvKategori.setText("Sangat Redup");
                tvCahaya.setText(sensorEvent.values[0] + " lux");
                img.setImageResource(R.drawable.sangatredup);
            }
            else if (sensorEvent.values[0] >= 40 && sensorEvent.values[0] <80.0) {
                tvKategori.setText("Redup");
                tvCahaya.setText(sensorEvent.values[0] + " lux");
                img.setImageResource(R.drawable.redup);
            }
            else if (sensorEvent.values[0] >= 80 && sensorEvent.values[0] <120.0) {
                tvKategori.setText("Normal");
                tvCahaya.setText(sensorEvent.values[0] + " lux");
                img.setImageResource(R.drawable.normal);
            }
            else if (sensorEvent.values[0] >= 120 && sensorEvent.values[0] <160.0) {
                tvKategori.setText("Cerah");
                tvCahaya.setText(sensorEvent.values[0] + " lux");
                img.setImageResource(R.drawable.cerah);
            }
            else if (sensorEvent.values[0] >= 160 && sensorEvent.values[0] <200.0) {
                tvKategori.setText("Sangat Cerah");
                tvCahaya.setText(sensorEvent.values[0] + " lux");
                img.setImageResource(R.drawable.sangatcerah);
            }
            else {
                tvKategori.setText("Waduh Silau");
                tvCahaya.setText(sensorEvent.values[0] + " lux");
                img.setImageResource(R.drawable.silau);
            }
        }
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {

    }
}