package com.google.android.gms.example.bannerexample.com.example.constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sand on 11/15/15.
 */
public class StoreData {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String DATABASE_NAME = "com.roznama";
    public static ArrayList<String>files=new ArrayList<String>();


    public  StoreData(Context context)
    {
        prefs = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
         editor = prefs.edit();
    }

    public void setFiles(ArrayList<String> filesList)
    {
        if(files!=null) {
            files.clear();
            files = filesList;
        }
    }

    public ArrayList<String> getFiles()
    {
        return files;
    }

//    public void saveTextSizeofFirstPage(String value)
//    {
//        editor.putString("textsize_first", value);
//        editor.commit();
//    }
//
//    public  String getTextSizeOfFirstPage()
//    {
//        String size=prefs.getString("textsize_first","");
//        return  size;
//    }
    public void saveTextSize(float textsize)
    {
        editor.putFloat("textsize",textsize);
        editor.commit();
    }
    public  float getTextSize()
    {
        float size=prefs.getFloat("textsize", 15);
        return  size;
    }

//    public void saveTextType(Typeface typeface)
//    {
//        editor.putString("typeface",typeface.toString());
//        editor.commit();
//    }

}
