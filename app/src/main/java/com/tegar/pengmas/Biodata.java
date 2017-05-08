package com.tegar.pengmas;

/**
 * Created by j on 18/11/2016.
 */

public class Biodata {
    private int _id;
    private String nama_lengkap, alamat, tanggal_lahir, tempat_lahir,
            tempat1, tempat2, tempat3;
    private double lat, lng, lat_1, lng_1, lat_2, lng_2, lat_3, lng_3;

    public Biodata(String nama_lengkap, String alamat, String tanggal_lahir, String tempat_lahir, String tempat1, String tempat2, String tempat3, double lat, double lng, double lat_1, double lng_1, double lat_2, double lng_2, double lat_3, double lng_3) {

        this.nama_lengkap = nama_lengkap;
        this.alamat = alamat;
        this.tanggal_lahir = tanggal_lahir;
        this.tempat_lahir = tempat_lahir;
        this.tempat1 = tempat1;
        this.tempat2 = tempat2;
        this.tempat3 = tempat3;
        this.lat = lat;
        this.lng = lng;
        this.lat_1 = lat_1;
        this.lng_1 = lng_1;
        this.lat_2 = lat_2;
        this.lng_2 = lng_2;
        this.lat_3 = lat_3;
        this.lng_3 = lng_3;
    }

    public Biodata() {}

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTempat1() {
        return tempat1;
    }

    public void setTempat1(String tempat1) {
        this.tempat1 = tempat1;
    }

    public String getTempat2() {
        return tempat2;
    }

    public void setTempat2(String tempat2) {
        this.tempat2 = tempat2;
    }

    public String getTempat3() {
        return tempat3;
    }

    public void setTempat3(String tempat3) {
        this.tempat3 = tempat3;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat_1() {
        return lat_1;
    }

    public void setLat_1(double lat_1) {
        this.lat_1 = lat_1;
    }

    public double getLng_1() {
        return lng_1;
    }

    public void setLng_1(double lng_1) {
        this.lng_1 = lng_1;
    }

    public double getLat_2() {
        return lat_2;
    }

    public void setLat_2(double lat_2) {
        this.lat_2 = lat_2;
    }

    public double getLng_2() {
        return lng_2;
    }

    public void setLng_2(double lng_2) {
        this.lng_2 = lng_2;
    }

    public double getLat_3() {
        return lat_3;
    }

    public void setLat_3(double lat_3) {
        this.lat_3 = lat_3;
    }

    public double getLng_3() {
        return lng_3;
    }

    public void setLng_3(double lng_3) {
        this.lng_3 = lng_3;
    }
}
