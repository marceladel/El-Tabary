/*
 * Copyright (C) 2013 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.example.bannerexample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.example.bannerexample.com.example.constant.ArabicNormalizer;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.constant.NavDrawerItem;
import com.google.android.gms.example.bannerexample.com.example.customviews.ListAdapter;
import com.google.android.gms.example.bannerexample.com.example.customviews.NavDrawerListAdapter;
import com.google.android.gms.example.bannerexample.com.example.parsing.Data;
import com.google.android.gms.example.bannerexample.com.example.parsing.DataElements;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Main Activity. Inflates main activity xml and child fragments.
 */

/**
 * Automatically generated file. DO NOT MODIFY
 */


public class CreateFile extends Activity implements AdapterView.OnItemClickListener {

    private AdView mAdView;
    String fileText;
    ArrayList<String> headerNamesList = new ArrayList<String>();
    ArrayList<String> idvalues = new ArrayList<String>();
    ArrayList<String> ayatNumbers = new ArrayList<String>();
    GridView gridview;
    String content = null;
    RelativeLayout header1;
    RelativeLayout header2;
    ArrayList<DataElements> dataElementses = new ArrayList<DataElements>();
    ArrayList<String> fileNamesList = new ArrayList<String>();
    ArrayList<String> fileHeaders = new ArrayList<String>();
    ArrayList<String> fileSupHeaders = new ArrayList<String>();
    ArrayList<String> newFileHeaders = new ArrayList<String>();
    ArrayList<String> newFileSupHeaders = new ArrayList<String>();
    String fileString = "";
    String FileContent;
    ArrayList<String> dataList = new ArrayList<String>();
    ArrayList<String> dataList2 = new ArrayList<String>();
    ArrayList<String> dataList3 = new ArrayList<String>();
    ArrayList<String> fileNamesUpdated = new ArrayList<String>();
    int counter;
    ArrayList<String> fileData = new ArrayList<String>();
    ArrayList<Integer> ayaNumbers = new ArrayList<Integer>();
    boolean matcher = false;
    ArrayList<String> sortedAyat = new ArrayList<String>();
    ArrayList<String> sortedSor = new ArrayList<String>();
    ArrayList<String> ayaNumbers2 = new ArrayList<String>();
    ListAdapter arrayAdapter;
    ArrayList<String> filesSorted = new ArrayList<String>();


    // main data

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
////
////		// Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));


        // Recycle the typed array
        navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(CreateFile.this,
                navDrawerItems,"");
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
//
//
        //getActionBar().setDisplayHomeAsUpEnabled(true);getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.menu, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }

//        mAdView = (AdView) findViewById(R.id.ad_view);
//        gridview = (GridView) findViewById(R.id.gridView);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();
//        try {
//            String fileContent = new Constants().readAssets(getApplicationContext(), "eltabary", "alldata.txt");
//            parseJson(fileContent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        customeGridView = new CustomeGridView(this, headerNamesList);
//        gridview.setAdapter(customeGridView);
//        gridview.setOnItemClickListener(this);


        //Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//                Toast.makeText(getApplicationContext(), "add closed", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                super.onAdFailedToLoad(errorCode);
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                super.onAdLeftApplication();
//            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//            }
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//            }
//        });

        LongOperation object = new LongOperation();
        object.execute();


    }



    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            } else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
        return false;
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                //fragment = new SearchScreen();
                break;

            case 4:
                //fragment = new BookmarkScreen();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            //  setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            getAssetsFileNames();
            Log.d("filesNumber", fileNamesList.size() + "");

            for (int i = 0; i < fileNamesList.size(); i++) {

                Log.d("filename",fileNamesList.get(i));
                FileContent = new Constants(getApplicationContext()).readAssets(getApplicationContext(), "eltabary", fileNamesList.get(i));
                parseHTML(FileContent, "h2",i);
                parseHTML2(FileContent, "h3");
                parseHTML3(FileContent, "div");
                FileContent = null;

            }
//            Log.d("data1size",dataList.size()+"");
//            Log.d("data2size",dataList2.size()+"");
//            Log.d("data3size",dataList3.size()+"");



//
//            fileHeaders = dataList;
//            fileSupHeaders = dataList2;
//
            String test;
            String test2;
//
//            // Log.d("fileContent",FileContent);
            for (int i = 0; i < dataList.size(); i++) {
                test = dataList.get(i);
                test = new ArabicNormalizer(test).getOutput();
                fileHeaders.add(test);
             //   Log.d("test",test);

            }
            for (int i = 0; i < dataList2.size(); i++) {

                test2 = dataList2.get(i);
                test2 = new ArabicNormalizer(test2).getOutput();
              //  Log.d("test2",test2);
                fileSupHeaders.add(test2);

            }

            generateNoteOnSD("search_file.txt", fileData.toString());

//
//            for (int i = 0; i <dataList.size(); i++) {
//               fileData.add("<h3>" + dataList.get(i) + "</h3>" + "<h2>" + dataList2.get(i)+"<h1>"+dataList3+"</h1>");
//            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Task finished", "msg");




        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }




    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    public ArrayList<String> parseHTML(String data, String tag,int pos) {
        String title = null;
        Document doc = Jsoup.parse(data);
        Elements headers = doc.select(tag);
        String newValue = null;
        if(headers.size()==0)
        {

            Log.d("FILENAME",fileNamesList.get(pos));

        }
        for (int i = 0; i < headers.size(); i++) {
            dataList.add(headers.get(i).text());

        }

        return dataList;
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

    public ArrayList<String> parseHTML2(String data, String tag) {
        String title = null;
        Document doc = Jsoup.parse(data);
        Elements headers = doc.select(tag);
        String newValue = null;
        for (int i = 0; i < headers.size(); i++) {
            newValue = headers.get(i).text();
            dataList2.add(headers.get(i).text());

        }

        return dataList2;
    }


//
//    public String readAssets(String FileName) {
//        StringBuilder buf = new StringBuilder();
//        InputStream json =
//                null;
//        try {
//            json = getAssets().open("eltabary/" + F);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BufferedReader in = null;
//
//        in = new BufferedReader(new InputStreamReader(json));
//
//        String str;
//
//        try {
//            while ((str = in.readLine()) != null) {
//                buf.append(str);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return buf.toString();
//    }

    //    /*parse html file*/
//    public void parseHTML(String data) {
//        String title = null;
//        Document doc = Jsoup.parse(data);
//        Elements headers = doc.select("h2");
//        String newValue = null;
//        for (int i = 0; i < headers.size(); i++) {
//            newValue = headers.get(i).text();
//            if (!headersNamesList.contains(newValue))
//
//            {
//                headersNamesList.add(newValue);
//            }
//        }
//
//        }
//
//    /*get assets file names*/
    public void getAssetsFileNames() {
        String filename = null;
        String[] fileNames =
                new String[0];
        try {
            fileNames = getAssets().list("eltabary");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String name : fileNames) {
            fileNamesList.add(name);

        }

    }
//
//    public void getAllHeaders() {
//        getAssetsFileNames();
//        int filesSize = fileNamesList.size();
//        String data = null;
//        for (int i = 0; i < filesSize; i++) {
//            data = readAssets(fileNamesList.get(i));
//            parseHTML(data);
//            data = null;
//        }
//    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public void parseJson(String content) {
        Gson gson = new Gson();
        Data data = gson.fromJson(content.trim(), Data.class);
        dataElementses = data.index;

        for (int i = 0; i < dataElementses.size(); i++) {
            headerNamesList.add(dataElementses.get(i).name);
            ayatNumbers.add(dataElementses.get(i).number);
            idvalues.add(dataElementses.get(i).id);
        }
    }

    public void generateNoteOnSD(String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            JSONObject search = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject studentsObj = new JSONObject();


//            ayaNumbers = new Constants(getApplicationContext()).extractNumbers(fileSupHeaders);
//            Collections.sort(ayaNumbers);
//          //  Collections.reverse(ayaNumbers);
//            for (int i = 0; i < ayaNumbers.size(); i++) {
//                ayaNumbers2.add(ayaNumbers.get(i).toString());
//            }
//
//            Log.d("numbers are", ayaNumbers2.toString());
//
//            for (int i = 0; i < ayaNumbers2.size(); i++) {
//                Log.d("value is", ayaNumbers2.get(i));
//
//                for (int j = 0; j < fileSupHeaders.size(); j++) {
//
//                    if (fileSupHeaders.get(j).matches(".*\\b" + ayaNumbers2.get(i) + "\\b.*")) {
//                        Log.d("MATCHER IS", fileSupHeaders.get(j));
//                        sortedAyat.add(0, fileSupHeaders.get(j));
//                        Log.d("aya", fileSupHeaders.get(j));
//                        filesSorted.add(0, dataList3.get(j));
//                        sortedSor.add(0,fileHeaders.get(j));
//
//                    }
//                }
//                matcher = false;
//
//            }


            for (int i = 0; i < dataList.size(); i++) {
                try {
                    search = new JSONObject();
                    search.put("aya", fileSupHeaders.get(i));
                    search.put("sora", fileHeaders.get(i));
                    search.put("filename",  dataList3.get(i));
                    jsonArray.put(search);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // writer.append("{"+"\"header1\":" + fileHeaders.get(i) + ","+ "\"header2\":"  + fileSupHeaders.get(i) +","+ "\"header3\":"  + dataList3.get(i) + "} ,");
            }

            try {
                studentsObj.put("data", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            String jsonStr = studentsObj.toString();
            writer.append(jsonStr);


//            System.out.println("jsonString: "+jsonStr);
//            writer.append("]}");
            writer.flush();
            writer.close();
          //  Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
//            importError = e.getMessage();
//            iError();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Log.d("item Clicked", "");
//        Intent gotoAyatPage = new Intent(getApplicationContext(), AyatActivity.class);
//        gotoAyatPage.putExtra("ayat_numbers", ayatNumbers.get(position));
//        gotoAyatPage.putExtra("id", idvalues.get(position));
//        startActivity(gotoAyatPage);

//        Intent test= new Intent(getApplicationContext(), Test2.class);
//        startActivity(test);
    }
}
