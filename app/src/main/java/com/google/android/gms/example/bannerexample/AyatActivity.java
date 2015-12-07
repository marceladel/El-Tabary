package com.google.android.gms.example.bannerexample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.gms.example.bannerexample.com.example.customviews.CustomGridView2;

import java.util.ArrayList;

public class AyatActivity extends Activity implements AdapterView.OnItemClickListener ,View.OnClickListener{

    GridView gridview;
    CustomGridView2 customeGridView;
    int numberOfAyat;
    ArrayList<String> ayatNumbersList = new ArrayList<String>();
    String idValue;
    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
          // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ayat);
        home=(ImageButton)findViewById(R.id.home);
        home.setOnClickListener(this);
        gridview = (GridView) findViewById(R.id.gridView);

        Intent intent = getIntent();
        String ayatNumbers = intent.getStringExtra("ayat_numbers");
        numberOfAyat = Integer.valueOf(ayatNumbers);
        idValue = intent.getStringExtra("id");
        String value;
        //١٢٣٤٥٦٧٨٩‎٠

        for (int i = 1; i <= numberOfAyat; i++) {
            value=String.valueOf(i);
            value=value.replace("0","٠").replace("1","١").replace("2", "٢").replace("3","٣").replace("4", "٤").replace("5", "٥")
                    .replace("6", "٦").replace("7", "٧").replace("8", "٨").replace("9", "٩");
            ayatNumbersList.add(value);
        }

        customeGridView = new CustomGridView2(this, ayatNumbersList);
        gridview.setAdapter(customeGridView);
        gridview.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ayat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       ProgressBar progress=new ProgressBar(this);

        Intent gotoMainContent = new Intent(getApplicationContext(), MainContentPage.class);
        gotoMainContent.putExtra("aya_num", ayatNumbersList.get(position));
        gotoMainContent.putExtra("id", idValue);
        gotoMainContent.putExtra("numberOfAyat", String.valueOf(numberOfAyat));
        startActivity(gotoMainContent);
    }

    @Override
    public void onClick(View v) {

    }
//
//    @Override
//    public void onClick(View v) {
//        if(v.getId()==R.id.home)
//        {
//            Intent gotoMain = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(gotoMain);
//        }
//
//    }
}
