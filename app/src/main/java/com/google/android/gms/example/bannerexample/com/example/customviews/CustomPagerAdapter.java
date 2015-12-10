package com.google.android.gms.example.bannerexample.com.example.customviews;

import android.content.Context;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

//import com.google.android.gms.example.bannerexample.MainActivity;
import com.google.android.gms.example.bannerexample.MainContentPage;
import com.google.android.gms.example.bannerexample.R;
import com.google.android.gms.example.bannerexample.TextFragment;
import com.google.android.gms.example.bannerexample.com.android.tabary.database.DataBaseHelper;
import com.google.android.gms.example.bannerexample.com.android.tabary.database.FileData;
import com.google.android.gms.example.bannerexample.com.example.constant.ArabicNormalizer;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.constant.StoreData;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<CustomListView> customLists = new ArrayList<CustomListView>();
    int NumberOfPages;
    LinearLayout tabLayout;

    ArrayList<String> filenames = new ArrayList<String>();
    ArrayList<String> soranames = new ArrayList<String>();
    ArrayList<String> ayatnames = new ArrayList<String>();
    private int mCount;
    private boolean doNotifyDataSetChangedOnce = false;
    RelativeLayout r1;
    RelativeLayout r2;
    RelativeLayout r3;
    RelativeLayout r4;
    RelativeLayout r5;
    RelativeLayout r6;
    DataBaseHelper db;
    boolean tashkelFlag;




    public CustomPagerAdapter(Context context, ArrayList<CustomListView> customLists, int NumberOfPages) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.customLists = customLists;
        doNotifyDataSetChangedOnce = true;
        this.NumberOfPages = NumberOfPages;
        db = new DataBaseHelper(context);
        tashkelFlag = new StoreData(context).getTashkel();

    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }


    @Override
    public int getCount() {

        Log.d("count is", customLists.size() + "");
        return customLists.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        tashkelFlag = new StoreData(mContext).getTashkel();

        Log.d("position of init", position + "");
        View itemView = null;
        if (itemView == null) {
            itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        }

        Log.d("position", position + "");

        ListView listView = (ListView) itemView.findViewById(R.id.list);
        listView.setAdapter(customLists.get(position));
        container.addView(itemView);



        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}