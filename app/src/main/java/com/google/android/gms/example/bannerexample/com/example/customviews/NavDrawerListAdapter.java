package com.google.android.gms.example.bannerexample.com.example.customviews;

/**
 * Created by sand on 11/16/15.
 */


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.example.bannerexample.BookMarkActivity;
//import com.google.android.gms.example.bannerexample.MainActivity;
import com.google.android.gms.example.bannerexample.MainActivityPage;
import com.google.android.gms.example.bannerexample.MainContentPage;
import com.google.android.gms.example.bannerexample.R;
import com.google.android.gms.example.bannerexample.SearchActivity;
import com.google.android.gms.example.bannerexample.TextFragment;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.constant.NavDrawerItem;
import com.google.android.gms.example.bannerexample.com.example.constant.StoreData;

import java.util.ArrayList;

public class NavDrawerListAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<NavDrawerItem> navDrawerItems;
    String flag;
    int defultProgress = 10;

    public NavDrawerListAdapter(Activity context, ArrayList<NavDrawerItem> navDrawerItems, String flag) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);

        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Log.d("itemsCount",navDrawerItems.size()+"");
        int height = displayMetrics.heightPixels/(navDrawerItems.size()+1);

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        SeekBar seekBar = (SeekBar) convertView.findViewById(R.id.seekbar);

        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        txtTitle.setText(navDrawerItems.get(position).getTitle());
        txtTitle.setTypeface(new StoreData(context).getTypeFaceStandard(context));

        LinearLayout drawer = (LinearLayout) convertView.findViewById(R.id.drawer_layout);
        drawer.getLayoutParams().height = height;
        seekBar.setVisibility(View.INVISIBLE);
        seekBar.setProgress((int)new StoreData(context).getTextSize());

        if (flag.equals("MainContent") && position == 3) {
            seekBar.setLayoutParams(params);
            seekBar.setVisibility(View.VISIBLE);

        }

        if (flag.equals("Search") && position == 1) {
            seekBar.setLayoutParams(params);
            seekBar.setVisibility(View.VISIBLE);

        }

        if(flag.equals("Bookmark")&& position==3)
        {
            seekBar.setLayoutParams(params);
            seekBar.setVisibility(View.VISIBLE);
        }
//        if (position == 2) {
//            seekBar.setVisibility(View.VISIBLE);
//        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(final SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                if (progressChanged < defultProgress) {
                    progressChanged = defultProgress;
                }

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setProgress(progressChanged);
                    }
                });


                Log.d("inside progress", progressChanged + "");

//                if(flag.equals("MainContent")) {
//                    MainActivityPage.list.setTextSize(progressChanged);
//                    MainActivityPage.list.notifyDataSetChanged();
//                }
                if (flag.equals("Search")) {
                    try {

                        SearchActivity.adapter2.setTextSize(progressChanged);
                        SearchActivity.adapter2.notifyDataSetChanged();
                    } catch (Exception e) {

                    }


                }
                if (flag.equals("Bookmark")) {
                    try {
                        BookMarkActivity.list.setTextSize(progressChanged);
                        BookMarkActivity.list.notifyDataSetChanged();
                    } catch (Exception e) {

                    }


                }

                if (flag.equals("MainContent")) {
                    TextFragment.customListView.setTextSize(progressChanged);
                    TextFragment.customListView.notifyDataSetChanged();
                    MainContentPage.mAdapter.notifyDataSetChanged();


                }

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(SeekbarActivity.this, "seek bar progress:" + progressChanged,
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // displaying count
        // check whether it set visible or not
//        if(navDrawerItems.get(position).getCounterVisibility()){
//            txtCount.setText(navDrawerItems.get(position).getCount());
//        }else{
//            // hide the counter view
//            txtCount.setVisibility(View.GONE);
//        }

        return convertView;
    }

    private class SeekbarTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

        //
        @Override
        protected String doInBackground(String... params) {


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {



        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }





    }