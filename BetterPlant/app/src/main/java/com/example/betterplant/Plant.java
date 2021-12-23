package com.example.betterplant;

import java.util.ArrayList;

public class Plant {
    String nama, cahaya, deskripsi;
    ArrayList<String> provinsi;

    public String getNama() {
        return nama;
    }

    public String getCahaya() {
        return cahaya;
    }

    public ArrayList<String> getProvinsi() {
        return provinsi;
    }

    public String getDeskripsi() { return deskripsi; }
}
