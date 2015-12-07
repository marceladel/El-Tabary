package com.google.android.gms.example.bannerexample.com.android.tabary.database;

/**
 * Created by sand on 11/22/15.
 */
public class FileData {


    private int id;
    private String filename;
    private String header;
    private String supheader;

    public FileData(){}


    public FileData(int id,String filename, String header,String supHeader) {
        super();
        this.filename = filename;
        this.header = header;
        this.supheader=supHeader;
        this.id=id;
    }

    public FileData(String filename, String header,String supHeader) {
        super();
        this.filename = filename;
        this.header = header;
        this.supheader=supHeader;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }

    public String getSupHeader() {
        return supheader;
    }
    public void setSupheader(String supheader) {
        this.supheader = supheader;
    }

    @Override
    public String toString() {
        return "DATA [id=" + id + ", title=" + filename + ", header=" + header+ ", supheader=" + supheader
                + "]";
    }
}
