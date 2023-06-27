package com.seiagul.adminelapor;

public class Data {
    private String id, pelaporList, kategoriList, rincianList, kordinatList;
    private String gambarList;
    public Data() {

    }

    public Data(String id, String pelaporList, String kategoriList, String rincianList, String kordinatList, String gambarList){
        this.id = id;
        this.pelaporList = pelaporList;
        this.kategoriList = kategoriList;
        this.kordinatList = kordinatList;
        this.rincianList = rincianList;
        this.gambarList = gambarList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPelaporList() {
        return pelaporList;
    }

    public void setPelaporList(String pelaporList) {
        this.pelaporList = pelaporList;
    }

    public String getKategoriList() {
        return kategoriList;
    }

    public void setKategoriList(String kategoriList) {
        this.kategoriList = kategoriList;
    }

    public String getRincianList() {
        return rincianList;
    }

    public void setRincianList(String rincianList) {
        this.rincianList = rincianList;
    }

    public String getKordinatList() {
        return kordinatList;
    }

    public void setKordinatList(String kordinatList) {
        this.kordinatList = kordinatList;
    }

    public String getGambarList() {
        return gambarList;
    }

    public void setGambarList(String gambarList) {
        this.gambarList = gambarList;
    }
}
