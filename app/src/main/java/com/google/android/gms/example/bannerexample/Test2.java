package com.google.android.gms.example.bannerexample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.parsing.SearchData;
import com.google.android.gms.example.bannerexample.com.example.parsing.SearchDataArrayElements;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Test2 extends Activity {


    ArrayList<String> ayatList = new ArrayList<String>();
    ArrayList<String> soraList = new ArrayList<String>();
    ArrayList<String> files = new ArrayList<String>();
    String fileContent = null;
    String results= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        SearchTask searchRes =new SearchTask();
        searchRes.execute();
    }

    private class SearchTask extends AsyncTask<String, Void, String> {
        //
        @Override
        protected String doInBackground(String... params) {

            ArrayList<SearchDataArrayElements> searchElemnts;

            try {
                fileContent = new Constants(getApplicationContext()).readAssets(getApplicationContext(), "eltabary", "search_file.txt");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            SearchData searchData = gson.fromJson(fileContent, SearchData.class);
            searchElemnts = searchData.data;

            for (int i = 0; i < searchElemnts.size(); i++) {
                ayatList.add(searchElemnts.get(i).aya);
                soraList.add(searchElemnts.get(i).sora);
                files.add(searchElemnts.get(i).fileName);
            }

            results= search("الله الذي يتوفاكم ",ayatList).toString();

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("excuted","msg");
            Log.d("resultsss", results);


        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public ArrayList<String> search(String input, ArrayList<String> dataList) {
        ArrayList<String> resList = new ArrayList<String>();

        for (String curVal : dataList) {
            if (curVal.contains(input)) {
                resList.add(curVal);
            }
        }
        return resList;
    }
}
