package com.google.android.gms.example.bannerexample;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.constant.NavDrawerItem;
import com.google.android.gms.example.bannerexample.com.example.constant.StoreData;
import com.google.android.gms.example.bannerexample.com.example.customviews.NavDrawerListAdapter;
import com.google.android.gms.example.bannerexample.com.example.parsing.DataElements;

import java.util.ArrayList;

public class MainContentPage extends FragmentActivity {
    public static int ITEMS = 300;
    private static Context myContext;
    public static MyAdapter mAdapter;
    public static ViewPager mPager;
    public ArrayList<String> files;
    public static int currentItem;
    RelativeLayout header;
    TextView header1;
    TextView header2;
    ArrayList<String> headers;
    ArrayList<String> headersWithoutTashkel;
    boolean hideTashkel;
    RelativeLayout layout;
    RelativeLayout layout2;
    ImageButton back;
    ImageButton menu;
    DrawerLayout.LayoutParams params;
    DrawerLayout.LayoutParams params2;

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
    public static MainContentPage context;
    public static int position2;
    boolean tashkelFlagClicked = false;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = getApplicationContext();
        setContentView(R.layout.activity_view_pager);
        context = this;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final int screenWidth = metrics.widthPixels;
        final int screenHeight = metrics.heightPixels;


        mAdView = (AdView) findViewById(R.id.ad_view);
//         Create an ad request. Check logcat output for the hashed device ID to
//         get test ads on a physical device. e.g.
//         "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

     //   Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Toast.makeText(getApplicationContext(), "add closed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });

        header1 = (TextView) findViewById(R.id.header1);
        header2 = (TextView) findViewById(R.id.header2);

        header2.setTypeface(new Constants(this).getHeaderTypeFace(this));
        header1.setTypeface(new Constants(this).getHeaderTypeFace(this));

        back = (ImageButton) findViewById(R.id.back);
        menu = (ImageButton) findViewById(R.id.menu_button);
        header = (RelativeLayout) findViewById(R.id.header);
        layout = (RelativeLayout) findViewById(R.id.linear1);
        layout2 = (RelativeLayout) findViewById(R.id.linear2);

        params = new DrawerLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params2 = new DrawerLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent gotoMainPage=new Intent(getApplicationContext(), MainActivityPage.class);
//                startActivity(gotoMainPage);

                finish();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
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
                            // Log.d("sssssssHeight", String.valueOf(allHeight));
                            // Log.d("ssssssWidth", String.valueOf(allWiDTH));
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
                            //   Log.d("height is", height + "");
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
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        // What's hot, We  will add a counter here


        // Recycle the typed array
      //  navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(MainContentPage.this,
                navDrawerItems, "MainContent");
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


//        if (savedInstanceState == null) {
//            // on first time display view for first nav item
//            displayView(0);
//        }


        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        Intent getData = getIntent();
        currentItem = getData.getIntExtra("pos", 0);
       // mPager.setOffscreenPageLimit(3);
        mPager.setCurrentItem(currentItem);
        files = getData.getStringArrayListExtra("files");
        ITEMS = files.size();
        mAdapter.notifyDataSetChanged();
        new StoreData(this).setFiles(files);

    }



    public static Context getAppContext() {
        return myContext;
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {

            return ITEMS;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


        @Override
        public Fragment getItem(int position) {
            System.out.println("position  = " + position);
            System.out.println("items length = " + getCount());


            if (position >= ITEMS)
                position = ITEMS - 1;

            position2 = position;

            return TextFragment.init(position);

			/*switch (position) {
            case 0: // Fragment # 0 - This will show image
				return ImageFragment.init(position);
			default:// Fragment # 2-9 - Will show list
				return ImageFragment.init(position);
			}*/
        }
    }

/*	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
*/

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
                finish();
                Intent gotoMain = new Intent(getApplicationContext(), MainActivityPage.class);
                startActivity(gotoMain);
                break;
            case 1:

                finish();
                Intent gotoSearch = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(gotoSearch);


                break;

            case 2:
                if (tashkelFlagClicked == false) {
                    tashkelFlagClicked = true;
                    navDrawerItems.set(2, new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(2, -1)));
                    // Recycle the typed array
//                    navMenuIcons.recycle();
                    adapter.notifyDataSetChanged();

                    new Constants(getApplicationContext()).setTashkel(true);
                    TextFragment.customListView.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();
                } else {
                    tashkelFlagClicked = false;
                    navDrawerItems.set(2,new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
                    // Recycle the typed array
//                    navMenuIcons.recycle();
                    adapter.notifyDataSetChanged();
                    new Constants(getApplicationContext()).setTashkel(false);
                    TextFragment.customListView.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();
                }
                break;

            case 4:
                TextFragment.callChangeFont();

                break;

            case 5:
                finish();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();

//        try{
//            Intent get=getIntent();
//            boolean mainActivity= get.getBooleanExtra("activityflag",false);
//            if(mainActivity==true) {
//                Intent gotoMainPage = new Intent(getApplicationContext(), MainActivityPage.class);
//                startActivity(gotoMainPage);
//            }
//        }
//        catch (Exception e)
//        {
//
//        }

    }

    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
    }

}