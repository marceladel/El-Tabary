package com.google.android.gms.example.bannerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.example.bannerexample.com.android.tabary.database.DataBaseHelper;
import com.google.android.gms.example.bannerexample.com.android.tabary.database.FileData;
import com.google.android.gms.example.bannerexample.com.example.constant.ArabicNormalizer;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.constant.NavDrawerItem;
import com.google.android.gms.example.bannerexample.com.example.customviews.BookmarListView;
import com.google.android.gms.example.bannerexample.com.example.customviews.NavDrawerListAdapter;
import com.google.android.gms.example.bannerexample.com.example.customviews.SearchAdapter;
import com.google.android.gms.example.bannerexample.com.example.parsing.DataElements;

import java.util.ArrayList;
import java.util.List;

public class BookMarkActivity extends Activity {

    ListView listView;
    ArrayList<String> files = new ArrayList<String>();
    ArrayList<String> headers = new ArrayList<String>();
    ArrayList<String> supheaders = new ArrayList<String>();
    DataBaseHelper db;
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
    RelativeLayout header;
    RelativeLayout layout;
    RelativeLayout layout2;
    ImageButton back;
    ImageButton menu;
    boolean hideTashkel;
    private NavDrawerListAdapter adapter;
    ArrayList<String> ayatWithoutTashkel = new ArrayList<String>();
    ArrayList<String> soraWithoutTashkel = new ArrayList<String>();
    public static BookmarListView list;
    DrawerLayout.LayoutParams params;
    TextView header1;
    TextView header2;
    TextView textNoRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        hideTashkel=new Constants(this).getTashkel();
        header1 = (TextView) findViewById(R.id.header1);
        header2 = (TextView) findViewById(R.id.header2);
        textNoRes=(TextView)findViewById(R.id.text);
        textNoRes.setVisibility(View.INVISIBLE);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        final int screenWidth = metrics.widthPixels;
        final int screenHeight = metrics.heightPixels;

        header2.setTypeface(new Constants(this).getHeaderTypeFace(this));
        header1.setTypeface(new Constants(this).getHeaderTypeFace(this));
        back = (ImageButton) findViewById(R.id.back);
        menu = (ImageButton) findViewById(R.id.menu_button);
        header = (RelativeLayout) findViewById(R.id.header);
        layout = (RelativeLayout) findViewById(R.id.linear1);
        layout2 = (RelativeLayout) findViewById(R.id.linear2);
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


        listView = (ListView) findViewById(R.id.list);
        db = new DataBaseHelper(this);
        Log.d("Reading: ", "Reading all contacts..");
        List<FileData> contacts = db.getAllContacts();

        for (FileData cn : contacts) {
            files.add(cn.getFilename());
            headers.add(cn.getHeader());
            supheaders.add(cn.getSupHeader());
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getFilename() + " ,header: " + cn.getHeader() + " ,supheader: " + cn.getSupHeader();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }



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
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
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
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here

        // Recycle the typed array
      //  navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(BookMarkActivity.this,
                navDrawerItems, "Bookmark");
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
    protected  void onResume()
    {
        super.onResume();
       updateData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename = files.get(position);
                Intent gotoMainContent = new Intent(getApplicationContext(), MainContentPage.class);
                gotoMainContent.putStringArrayListExtra("files", files);
                gotoMainContent.putExtra("pos", position);
                startActivity(gotoMainContent);
            }
        });
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

    public void updateData()
    {
        hideTashkel=new Constants(getApplicationContext()).getTashkel();
        Log.d("case1", "msg");
        if (hideTashkel == false) {
            navDrawerItems.set(2, new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(2, -1)));
            // Recycle the typed array
//                    navMenuIcons.recycle();
            adapter.notifyDataSetChanged();
            new Constants(getApplicationContext()).setTashkel(true);
            String value;
            for (int i = 0; i < headers.size(); i++) {
                value = headers.get(i);
                value = new ArabicNormalizer(value).getOutput();
                soraWithoutTashkel.add(value);
            }
            for (int i = 0; i < supheaders.size(); i++) {
                value = supheaders.get(i);
                value = new ArabicNormalizer(value).getOutput();
                ayatWithoutTashkel.add(value);
            }

            list = new BookmarListView(this, ayatWithoutTashkel, soraWithoutTashkel, files);
            // list.notifyDataSetChanged();
            listView.setAdapter(list);


        } else {
            navDrawerItems.set(2, new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            // Recycle the typed array
//                    navMenuIcons.recycle();
            adapter.notifyDataSetChanged();
            new Constants(getApplicationContext()).setTashkel(false);
            list = new BookmarListView(this, supheaders, headers, files);
            listView.setAdapter(list);
            //list.notifyDataSetChanged();

        }
        if (headers.size()==0)
        {
            textNoRes.setVisibility(View.VISIBLE);
        }
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
            case 1:

                Intent gotoSearch = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(gotoSearch);
            //updateData();

                break;

            case 2:
                updateData();
                break;

            case 3:

                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                final int screenWidth = metrics.widthPixels;
                final int screenHeight = metrics.heightPixels;

                LayoutInflater layoutInflater = (LayoutInflater) this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View layout = layoutInflater.inflate(R.layout.change_font_popup, null);
                final PopupWindow popup = new PopupWindow(layout, screenWidth,
                        screenHeight , true);
                popup.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.popup_drawable));

                popup.setAnimationStyle(R.style.AnimationPopup);
                popup.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);

                TextView txt1 = (TextView) layout.findViewById(R.id.txt1);
                txt1.setTypeface(new Constants(getApplicationContext()).getTypeFaceStandard(getApplicationContext()));

                TextView txt2 = (TextView) layout.findViewById(R.id.txt2);
                txt2.setTypeface(new Constants(this).getTypeFaceThoulth(getApplicationContext()));
                txt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();
                        new Constants(getApplicationContext()).setTypeface(1);
                        list.notifyDataSetChanged();
                    }
                });
                TextView txt3 = (TextView) layout.findViewById(R.id.txt3);

                txt3.setTypeface(new Constants(this).getTypeFacediwany(getApplicationContext()));
                txt3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();
                        new Constants(getApplicationContext()).setTypeface(2);
                        list.notifyDataSetChanged();

                    }
                });
                TextView txt4 = (TextView) layout.findViewById(R.id.txt4);
                txt4.setTypeface(new Constants(this).getTypeFacenaskh(getApplicationContext()));
                txt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popup.dismiss();
                        new Constants(getApplicationContext()).setTypeface(3);
                        list.notifyDataSetChanged();


                    }
                });


                break;

            case 4:
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

}
