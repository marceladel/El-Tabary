package com.google.android.gms.example.bannerexample;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.example.bannerexample.com.example.constant.ArabicNormalizer;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.constant.NavDrawerItem;
import com.google.android.gms.example.bannerexample.com.example.constant.StoreData;
import com.google.android.gms.example.bannerexample.com.example.customviews.NavDrawerListAdapter;
import com.google.android.gms.example.bannerexample.com.example.customviews.SoraCustomList;
import com.google.android.gms.example.bannerexample.com.example.parsing.Data;
import com.google.android.gms.example.bannerexample.com.example.parsing.DataElements;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivityPage extends ListActivity {

    // main data

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
    ImageButton menuButton;
    TextView header1;
    TextView header2;
    TextView text1;
    TextView text2;
    boolean clickedtext1 = false;
    boolean clickedtext2 = false;
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> soraNames = new ArrayList<String>();
    ArrayList<String> soraNumbers = new ArrayList<String>();
    ArrayList<String> ayatNumbersList = new ArrayList<String>();
    public static SoraCustomList list;
    ArrayList<String> ayat = new ArrayList<String>();
    boolean hideTashkel = false;
    ArrayList<String> dataWithoutTashkel = new ArrayList<String>();
    RelativeLayout line;
    RelativeLayout header;
    DrawerLayout.LayoutParams params;
    RelativeLayout layout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //  getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        try {
            String fileContent = new Constants(this).readAssets(getApplicationContext(), "eltabary", "alldata.txt");
            parseJson(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        layout2 = (RelativeLayout) findViewById(R.id.linear2);


        list = new SoraCustomList(MainActivityPage.this, soraNames, ayatNumbersList,ids);
        setListAdapter(list);

        header1 = (TextView) findViewById(R.id.header1);
        header2 = (TextView) findViewById(R.id.header2);


        header2.setTypeface(new StoreData(this).getHeaderTypeFace(this));
        header1.setTypeface(new StoreData(this).getHeaderTypeFace(this));
        header = (RelativeLayout) findViewById(R.id.header);
        params = new DrawerLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        text1 = (TextView) findViewById(R.id.txtview1);
        text2 = (TextView) findViewById(R.id.txtview2);

        text1.setTypeface(new StoreData(this).getTypeFaceStandard(this));
        text2.setTypeface(new StoreData(this).getTypeFaceStandard(this));
        text1.setTextColor(getResources().getColor(R.color.lgrey_start));
        text2.setTextColor(getResources().getColor(R.color.Gray));

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2.setTextColor(getResources().getColor(R.color.lgrey_start));
                if (clickedtext1 == false) {
                    Intent gotoSearch = new Intent(getApplication(), SearchActivity.class);
                    startActivity(gotoSearch);
                    clickedtext1 = true;
                    text1.setTextColor(getResources().getColor(R.color.Gray));
                } else {
                    clickedtext1 = false;
                    text1.setTextColor(getResources().getColor(R.color.lgrey_start));

                }
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setTextColor(getResources().getColor(R.color.lgrey_start));
                if (clickedtext2 == false) {
                    clickedtext2 = true;
                    text2.setTextColor(getResources().getColor(R.color.Gray));
                } else {
                    clickedtext2 = false;
                    text2.setTextColor(getResources().getColor(R.color.lgrey_start));
                }
            }
        });

        line = (RelativeLayout) findViewById(R.id.line);
        ViewTreeObserver viewTreeObserver2 = text2
                .getViewTreeObserver();
        if (viewTreeObserver2.isAlive()) {
            viewTreeObserver2
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            text2.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                            int allHeight = text2.getHeight();
                            int allWiDTH = text2.getWidth();
                            // Log.d("sssssssHeight", String.valueOf(allHeight));
                            //  Log.d("ssssssWidth", String.valueOf(allWiDTH));
                            line.getLayoutParams().width = allWiDTH;


                        }
                    });
        }
        menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        final int screenWidth = metrics.widthPixels;
        final int screenHeight = metrics.heightPixels;

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
                            //Log.d("height is", height + "");
                            params.topMargin = height;
                            params.gravity = Gravity.RIGHT;
                            params.width=screenWidth-height;
                            mDrawerList.setLayoutParams(params);
                            layout2.getLayoutParams().width = height;
                            layout2.getLayoutParams().height = height;


                        }
                    });
        }

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Photos
        // navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        // What's hot, We  will add a counter here



        // Recycle the typed array
        navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(MainActivityPage.this,
                navDrawerItems, "MainPage");
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("inside listner", "msg");
                displayView(position);
                mDrawerLayout.closeDrawer(Gravity.RIGHT);

            }
        });

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
                Intent gotoSearch = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(gotoSearch);
                break;
            case 1:
                Log.d("case1", "msg");
                if (hideTashkel == false) {
                    navMenuTitles[1] = "اظهار التشكيل";
                    adapter.notifyDataSetChanged();
                    hideTashkel = true;
                    String value;
                    for (int i = 0; i < soraNames.size(); i++) {
                        value = soraNames.get(i);
                        value = new ArabicNormalizer(value).getOutput();
                        dataWithoutTashkel.add(value);
                    }
                    list = new SoraCustomList(MainActivityPage.this, dataWithoutTashkel, ayatNumbersList,ids);
                    setListAdapter(list);

                } else {
                    navMenuTitles[1] = "إخفاء التشكيل";
                    adapter.notifyDataSetChanged();
                    hideTashkel = false;
                    list = new SoraCustomList(MainActivityPage.this, soraNames, ayatNumbersList,ids);
                    setListAdapter(list);
                }

                break;

            case 2:
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                final int screenWidth = metrics.widthPixels;
                final int screenHeight = metrics.heightPixels;


                LayoutInflater layoutInflater = (LayoutInflater) this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View layout = layoutInflater.inflate(R.layout.change_font_popup, null);
                final PopupWindow popup = new PopupWindow(layout, screenWidth,
                        screenHeight, true);
                popup.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.popup_drawable));

                popup.setAnimationStyle(R.style.AnimationPopup);
                popup.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);

                TextView txt1 = (TextView) layout.findViewById(R.id.txt1);
                txt1.setTypeface(new StoreData(this).getTypeFaceStandard(getApplicationContext()));

                TextView txt2 = (TextView) layout.findViewById(R.id.txt2);
                txt2.setTypeface(new StoreData(this).getTypeFaceThoulth(getApplicationContext()));
                txt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();

                        list.setTypeFace(new StoreData(getApplicationContext()).getTypeFaceThoulth(getApplicationContext()));
                        list.notifyDataSetChanged();
                    }
                });
                TextView txt3 = (TextView) layout.findViewById(R.id.txt3);

                txt3.setTypeface(new StoreData(this).getTypeFacediwany(getApplicationContext()));
                txt3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();
                        list.setTypeFace(new StoreData(getApplicationContext()).getTypeFacediwany(getApplicationContext()));
                        list.notifyDataSetChanged();
                    }
                });
                TextView txt4 = (TextView) layout.findViewById(R.id.txt4);
                txt4.setTypeface(new StoreData(this).getTypeFacenaskh(getApplicationContext()));
                txt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popup.dismiss();
                        list.setTypeFace(new StoreData(getApplicationContext()).getTypeFacenaskh(getApplicationContext()));
                        list.notifyDataSetChanged();

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
    protected void onResume() {
        super.onResume();
        text1.setTextColor(getResources().getColor(R.color.lgrey_start));
        text2.setTextColor(getResources().getColor(R.color.Gray));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public void parseJson(String content) {
        Gson gson = new Gson();
        Data data = gson.fromJson(content.trim(), Data.class);
        dataElementses = data.index;

        for (int i = 0; i < dataElementses.size(); i++) {
            soraNames.add(dataElementses.get(i).name);
            soraNumbers.add(dataElementses.get(i).number);
            ids.add(dataElementses.get(i).id);
        }
        String value;
        for (int i = 1; i <= ids.size(); i++) {
            value = String.valueOf(i);
            value = value.replaceAll("0", "٠").replaceAll("1", "١").replaceAll("2", "٢").replaceAll("3", "٣").replaceAll("4", "٤").replaceAll("5", "٥")
                    .replaceAll("6", "٦").replaceAll("7", "٧").replaceAll("8", "٨").replaceAll("9", "٩");
            ayatNumbersList.add(value);
        }
    }

    public void parseInfoFile(String id ,String content)
    {

    }
}
