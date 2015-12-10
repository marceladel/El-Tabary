package com.google.android.gms.example.bannerexample.com.example.constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sand on 11/15/15.
 */
public class StoreData {
    public static ArrayList<String> files = new ArrayList<String>();
    float textSize;
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    Typeface typeface;
    boolean removetashkel;
    String accessToken;
    public static Typeface custom_font;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String DATABASE_NAME = "com.roznama";
    Context con;


    public StoreData(Context context) {
        prefs = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        con=context;
    }

    public void setFiles(ArrayList<String> filesList) {
        if (files != null) {
            files.clear();
            files = filesList;
        }
    }

    public ArrayList<String> getFiles() {
        return files;
    }


    public Typeface getTypeFaceStandard(Context context) {
        custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/DecoNaskh.ttf");
        return custom_font;
    }

    public Typeface getHeaderTypeFace(Context context) {
        custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/AdobeArabic.ttf");
        return custom_font;
    }

    public Typeface getTypeFaceThoulth(Context context) {
        custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/font_thuloth.TTF");
        return custom_font;
    }

    public Typeface getTypeFacediwany(Context context) {
        custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/font_diwany.TTF");
        return custom_font;
    }

    public Typeface getTypeFacenaskh(Context context) {
        custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/font_naskh.ttf");
        return custom_font;
    }

    public void setTextSize(float textSize) {
        editor.putFloat("textSize", textSize);
        editor.commit();
    }

    public float getTextSize() {

        textSize = prefs.getFloat("textSize", 20f);
        return textSize;
    }

    public void setTypeface(int typefacenumber) {
        editor.putInt("typeFaceNum", typefacenumber);
        editor.commit();

    }

    public Typeface getTypeface() {
        int typeFcaeNum = prefs.getInt("typeFaceNum", 0);
        Log.d("typfacenum", typeFcaeNum + "");
        switch (typeFcaeNum) {
            case 0:
                typeface = getTypeFaceStandard(con);
                break;
            case 1:
                typeface = getTypeFaceThoulth(con);

                break;
            case 2:
                typeface = getTypeFacediwany(con);
                break;
            case 3:
                typeface = getTypeFacenaskh(con);

            default:
                break;
        }
        return typeface;
    }

    public void setTashkel(boolean tashkel) {
        editor.putBoolean("tashkelFlag", tashkel);
        editor.commit();
    }

    public void saveAccessToken(String accessToken) {
        editor.putString("fb_access_token", accessToken);
        editor.commit();
    }


    public String getAccessToken() {

        accessToken = prefs.getString("fb_access_token", "");
        return accessToken;
    }

    public boolean getTashkel() {
        removetashkel = prefs.getBoolean("tashkelFlag", false);
        return removetashkel;
    }

}
