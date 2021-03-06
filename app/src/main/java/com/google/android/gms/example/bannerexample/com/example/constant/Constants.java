package com.google.android.gms.example.bannerexample.com.example.constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.example.bannerexample.BookMarkActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sand on 10/19/15.
 */
public class Constants {
    public static Typeface custom_font;
    public String PREFS_NAME = "com.data";
    ArrayList<String> dataList = new ArrayList<String>();
    ArrayList<String> dataList3 = new ArrayList<String>();
    ArrayList<String> getDataListNormalized = new ArrayList<String>();
    float textSize;
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    Typeface typeface;
    boolean removetashkel;
    String accessToken;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String DATABASE_NAME = "com.roznama";
    Context con;

    public Constants(Context context) {
        prefs = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        con=context;
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

//    public Typeface getStandardTypeFace(Context context) {
//
//        custom_font = Typeface.SANS_SERIF;
//        return custom_font;
//    }

    public ArrayList<String> NormalizeCode(ArrayList<String> arrayList) {
        String value;
        for (int i = 0; i < arrayList.size(); i++) {
            value = arrayList.get(i);
            value = new ArabicNormalizer(value).getOutput();
            getDataListNormalized.add(value);
        }

        return getDataListNormalized;

    }

    public ArrayList<String> arabicNumaricCode(ArrayList<String> data) {
        ArrayList<String> res = new ArrayList<String>();
        for (int k = 0; k < data.size(); k++) {
            String value3 = data.get(k).replaceAll("0", "٠").replaceAll("1", "١").replaceAll("2", "٢").replaceAll("3", "٣").replaceAll("4", "٤").replaceAll("5", "٥")
                    .replaceAll("6", "٦").replaceAll("7", "٧").replaceAll("8", "٨").replaceAll("9", "٩");
            res.add(value3);
        }

        return res;
    }
//
//    public ArrayList<Integer> arabicNumaricCode2(ArrayList<Integer> data) {
//        ArrayList<Integer> res = new ArrayList<Integer>();
//        for (int k = 0; k < data.size(); k++) {
//            int value3 = Integer.valueOf(data.get(k).toString().replaceAll("0", "٠").replaceAll("1", "١").replaceAll("2", "٢").replaceAll("3", "٣").replaceAll("4", "٤").replaceAll("5", "٥")
//                    .replaceAll("6", "٦").replaceAll("7", "٧").replaceAll("8", "٨").replaceAll("9", "٩"));
//            res.add(value3);
//            Log.d("for loop value3",value3+"");
//        }
//
//        Log.d("res array",res.toString());
//        return res;
//    }

    public String arabicNumaricCode(String value) {

        String value3 = value.replaceAll("0", "٠").replaceAll("1", "١").replaceAll("2", "٢").replaceAll("3", "٣").replaceAll("4", "٤").replaceAll("5", "٥")
                .replaceAll("6", "٦").replaceAll("7", "٧").replaceAll("8", "٨").replaceAll("9", "٩");

        return value3;
    }

    public ArrayList<String> parseHTML3(String data, String tag) {
        String title = null;
        Document doc = Jsoup.parse(data);
        Elements elements = doc.getElementsByTag("div");
        String value = null;
        for (int i = 0; i < elements.size(); i++) {
            value = elements.get(i).id();
            dataList3.add(value + ".txt");
        }

        return dataList3;
    }

    public String readAssets(Context context, String folderName, String fileName) {
        StringBuilder buf = new StringBuilder();
        InputStream json =
                null;
        try {
            json = context.getAssets().open(folderName + "/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;

        in = new BufferedReader(new InputStreamReader(json));

        String str;

        try {
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buf.toString();
    }


    public ArrayList<String> parseHTML(String data, String tag) {
        String title = null;
        Document doc = Jsoup.parse(data);
        Elements headers = doc.select(tag);
        String newValue = null;
        for (int i = 0; i < headers.size(); i++) {
            dataList.add(headers.get(i).text());
        }

        return dataList;
    }


    public void setTextSize(float textSize) {
        editor.putFloat("textSize", textSize);
        editor.commit();
    }

    public float getTextSize() {

        textSize = prefs.getFloat("textSize", 20f);
        return textSize;
    }


    public ArrayList<Integer> extractNumbers(ArrayList<String> list) {

        Pattern p = Pattern.compile("-?\\d+");
        for (int i = 0; i < list.size(); i++) {
            Matcher m = p.matcher(list.get(i));
            while (m.find()) {
                System.out.println(m.group());
                numbers.add(Integer.valueOf(m.group()));
            }
        }
        return numbers;
    }

    public int extractNumbers(String s) {

        Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(s);
            while (m.find()) {
                System.out.println(m.group());
                numbers.add(Integer.valueOf(m.group()));
            }

        return Integer.valueOf(m.group());
    }

    public void setTypeface(int typefacenumber) {
        editor.putInt("typeFaceNum", typefacenumber);
        editor.commit();

    }

    public Typeface getTypeface() {
        int typeFcaeNum= prefs.getInt("typeFaceNum",0);
        Log.d("typfacenum", typeFcaeNum + "");
        switch (typeFcaeNum)
        {
            case 0:
                typeface=getTypeFaceStandard(con);
                break;
            case 1:
                typeface=getTypeFaceThoulth(con);

                break;
            case 2:
                typeface=getTypeFacediwany(con);
                break;
            case 3:
                typeface=getTypeFacenaskh(con);

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
        removetashkel=prefs.getBoolean("tashkelFlag",false);
        return removetashkel;
    }
}
