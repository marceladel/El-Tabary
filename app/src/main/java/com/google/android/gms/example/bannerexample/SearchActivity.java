package com.google.android.gms.example.bannerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.example.bannerexample.com.example.constant.ArabicNormalizer;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.constant.NavDrawerItem;
import com.google.android.gms.example.bannerexample.com.example.customviews.NavDrawerListAdapter;
import com.google.android.gms.example.bannerexample.com.example.customviews.SearchAdapter;
import com.google.android.gms.example.bannerexample.com.example.customviews.SoraCustomList;
import com.google.android.gms.example.bannerexample.com.example.parsing.DataElements;
import com.google.android.gms.example.bannerexample.com.example.parsing.SearchData;
import com.google.android.gms.example.bannerexample.com.example.parsing.SearchDataArrayElements;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchActivity extends Activity implements AdapterView.OnItemClickListener {
    ArrayList<String> ayatList = new ArrayList<String>();
    ArrayList<String> soraList = new ArrayList<String>();
    ArrayList<String> files = new ArrayList<String>();
    String fileContent = null;
    String results = null;
    EditText input;
    ListView listView;
    String value;
    ArrayList<String> AyatsearchRes = new ArrayList<String>();
    ArrayList<String> SorasearchRes = new ArrayList<String>();
    ArrayList<String> FilesSearchRes = new ArrayList<String>();
    // RelativeLayout header;
    RelativeLayout layout;
    RelativeLayout layout2;
    ImageButton back;
    ImageButton menu;
    boolean hideTashkel = false;
    public static SearchAdapter adapter2;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;
    ArrayList<DataElements> dataElementses = new ArrayList<DataElements>();

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    ArrayList<String> ayatWithoutTashkel = new ArrayList<String>();
    ArrayList<String> soraWithoutTashkel = new ArrayList<String>();
    DrawerLayout.LayoutParams params;
    RelativeLayout header;
    TextView header1;
    TextView header2;
    TextView text;
    ArrayList<String> newData = new ArrayList<String>();
    TextView textNoResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        final int screenWidth = metrics.widthPixels;
        final int screenHeight = metrics.heightPixels;
        header1 = (TextView) findViewById(R.id.header1);
        header2 = (TextView) findViewById(R.id.header2);
        text = (TextView) findViewById(R.id.text);
        text.setTypeface(new Constants(this).getTypeFacenaskh(this));
        textNoResults=(TextView) findViewById(R.id.text2);
        textNoResults.setVisibility(View.INVISIBLE);

        header2.setTypeface(new Constants(this).getHeaderTypeFace(this));
        header1.setTypeface(new Constants(this).getHeaderTypeFace(this));

        back = (ImageButton) findViewById(R.id.back);
        menu = (ImageButton) findViewById(R.id.menu_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        header = (RelativeLayout) findViewById(R.id.header);
        params = new DrawerLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);


        input = (EditText) findViewById(R.id.input);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        header = (RelativeLayout) findViewById(R.id.header);
        layout = (RelativeLayout) findViewById(R.id.linear1);
        layout2 = (RelativeLayout) findViewById(R.id.linear2);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3) {
                    value = input.getText().toString();
                    Log.d("value is", value);
                    SearchTask object = new SearchTask();
                    object.execute();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ViewTreeObserver viewTreeObserver2 = header
                .getViewTreeObserver();
        if (viewTreeObserver2.isAlive()) {
            viewTreeObserver2
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            header.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                            int allHeight = header.getHeight();
                            int allWiDTH = header.getWidth();
                            layout.getLayoutParams().height = allHeight;
                            layout.getLayoutParams().width = allHeight;
                            layout2.getLayoutParams().height = allHeight;
                            layout2.getLayoutParams().width = allHeight;

                        }
                    });
        }

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        ViewTreeObserver viewTreeObserver3 = header
                .getViewTreeObserver();
        if (viewTreeObserver3.isAlive()) {
            viewTreeObserver3
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            int height = header.getHeight();
                            params.topMargin = height;
                            params.gravity = Gravity.RIGHT;
                            params.width=screenWidth-height;
                            mDrawerList.setLayoutParams(params);


                        }
                    });
        }

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(SearchActivity.this,
                navDrawerItems, "Search");
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("inside listner", "msg");
                displayView(position);
                mDrawerLayout.closeDrawer(Gravity.RIGHT);

            }
        });

        // enabling action bar app icon and behaving it as toggle button
//
        ;
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
//        //displaying custom ActionBar
//        View mActionBarView = getLayoutInflater().inflate(R.layout.actionbar_view, null);
//        actionBar.setCustomView(mActionBarView);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        View v =getActionBar().getCustomView();
//        /*** sample click is a id of the view i have used in action bar view ***/
//        ((ImageButton)v.findViewById(R.id.menu_button)).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                /*****Add your click function here******/
//
//
//            }
//        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.menu, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                // getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);





//        if (savedInstanceState == null) {
//            // on first time display view for first nav item
//            displayView(0);
//        }

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
        // menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        switch (position) {
            case 0:
                Intent gotoMain = new Intent(getApplicationContext(), MainActivityPage.class);
                startActivity(gotoMain);
                break;
        //   case 1:
//                Log.d("case1", "msg");
//                if (hideTashkel == false) {
//                    hideTashkel = true;
//                    String value;
//                    for (int i = 0; i < AyatsearchRes.size(); i++) {
//                        value = AyatsearchRes.get(i);
//                        value = new ArabicNormalizer(value).getOutput();
//                        ayatWithoutTashkel.add(value);
//                    }
//                    for (int i = 0; i < SorasearchRes.size(); i++) {
//                        value = SorasearchRes.get(i);
//                        value = new ArabicNormalizer(value).getOutput();
//                        soraWithoutTashkel.add(value);
//                    }
//
//                    adapter2 = new SearchAdapter(SearchActivity.this, ayatWithoutTashkel, soraWithoutTashkel, FilesSearchRes);
//                    listView.setAdapter(adapter2);
//
//
//                } else {
//                    hideTashkel = false;
//                    adapter2 = new SearchAdapter(SearchActivity.this, AyatsearchRes, SorasearchRes, FilesSearchRes);
//                    listView.setAdapter(adapter2);
//
//                }

               // break;

            case 2:
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                final int screenWidth = metrics.widthPixels;
                final int screenHeight = metrics.heightPixels;


                LayoutInflater layoutInflater = (LayoutInflater) this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View layout = layoutInflater.inflate(R.layout.change_font_popup, null);
                final PopupWindow popup = new PopupWindow(layout, screenWidth,
                        screenHeight, true);
                popup.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.popup_drawable));

                popup.setAnimationStyle(R.style.AnimationPopup);
                popup.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);

                TextView txt1 = (TextView) layout.findViewById(R.id.txt1);
                txt1.setTypeface(new Constants(getApplicationContext()).getTypeFaceStandard(getApplicationContext()));

                TextView txt2 = (TextView) layout.findViewById(R.id.txt2);
                txt2.setTypeface(new Constants(getApplicationContext()).getTypeFaceThoulth(getApplicationContext()));
                txt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("typface1 clicked","msg");
                        popup.dismiss();
                        adapter2.setTypeFace(1);
                        adapter2.notifyDataSetChanged();
                    }
                });
                TextView txt3 = (TextView) layout.findViewById(R.id.txt3);

                txt3.setTypeface(new Constants(getApplicationContext()).getTypeFacediwany(getApplicationContext()));
                txt3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();
                        adapter2.setTypeFace(2);
                        adapter2.notifyDataSetChanged();
                    }
                });
                TextView txt4 = (TextView) layout.findViewById(R.id.txt4);
                txt4.setTypeface(new Constants(getApplicationContext()).getTypeFacenaskh(getApplicationContext()));
                txt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popup.dismiss();
                        adapter2.setTypeFace(3);
                        adapter2.notifyDataSetChanged();

                    }
                });


                break;

            case 3:
                Intent gotoBookMark = new Intent(getApplicationContext(), BookMarkActivity.class);
                startActivity(gotoBookMark);
                break;

            default:
                break;
        }


    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        // getActionBar().setTitle(mTitle);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("fileResSize", FilesSearchRes.size() + "");

        String filename = FilesSearchRes.get(position);
        Intent gotoMainContent = new Intent(getApplicationContext(), MainContentPage.class);
        gotoMainContent.putStringArrayListExtra("files", FilesSearchRes);
        gotoMainContent.putExtra("pos", position);
        startActivity(gotoMainContent);

    }

    private class SearchTask extends AsyncTask<String, Void, String> {
        //
        @Override
        protected void onPreExecute() {

            AyatsearchRes.clear();
            SorasearchRes.clear();
            FilesSearchRes.clear();
            newData.clear();
            ayatList.clear();
            soraList.clear();
            files.clear();
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<SearchDataArrayElements> searchElemnts;

            try {
                fileContent = new Constants(getApplicationContext()).readAssets(getApplicationContext(), "eltabary", "search_file.txt");
                Gson gson = new Gson();
                SearchData searchData = gson.fromJson(fileContent, SearchData.class);
                searchElemnts = searchData.data;

                for (int i = 0; i < searchElemnts.size(); i++) {
                    ayatList.add(searchElemnts.get(i).aya);
                    soraList.add(searchElemnts.get(i).sora);
                    files.add(searchElemnts.get(i).fileName);
                }


                search(value, ayatList);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            if(AyatsearchRes.size()==0)
            {
                Log.d("inside no res","msg");
                textNoResults.bringToFront();
                textNoResults.setVisibility(View.VISIBLE);
            }
            else {
                textNoResults.setVisibility(View.INVISIBLE);
                try {
                    for (int i = 0; i < AyatsearchRes.size(); i++) {
                        value = AyatsearchRes.get(i);
                        if (value.length() > 40) {
                            value = value.substring(0, 40) + " ...";
                        } else {
                            value = AyatsearchRes.get(i);
                        }
                        newData.add(value);
                    }

                    Log.d("ayatsize", newData.size() + "");
                    Log.d("soralist", SorasearchRes.size() + "");
                    Log.d("fileslist", FilesSearchRes.size() + "");


                    Log.d("excuted", "msg");
                    adapter2 = new SearchAdapter(SearchActivity.this, newData, SorasearchRes, FilesSearchRes);
                    listView.setAdapter(adapter2);
                } catch (Exception e) {

                }
            }


        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void search(String input, ArrayList<String> dataList) {
        ArrayList<String> resList = new ArrayList<String>();
        String curVal;
        String value;

        for (int i = 0; i < dataList.size(); i++) {
            curVal = dataList.get(i);
            if (curVal.contains(input)) {
                //resList.add(curVal);
                if (!AyatsearchRes.contains(curVal)) {
                    AyatsearchRes.add(curVal);
                    SorasearchRes.add(soraList.get(i));
                    FilesSearchRes.add(files.get(i));

                }

            }
        }


    }
}
