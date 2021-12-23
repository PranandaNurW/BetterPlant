package com.example.recycleview;

import java.util.ArrayList;
import java.util.Iterator;

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
