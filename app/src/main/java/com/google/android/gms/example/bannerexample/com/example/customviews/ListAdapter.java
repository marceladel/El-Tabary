package com.google.android.gms.example.bannerexample.com.example.customviews;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.example.bannerexample.R;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;

import java.util.ArrayList;

/**
 * Created by sand on 11/17/15.
 */
public class ListAdapter extends ArrayAdapter<String> {

    static Activity context;
    ArrayList<String> ayatList = new ArrayList<String>();


    private static LayoutInflater inflater = null;

    public ListAdapter(Activity context, ArrayList<String> ayatListList) {
        super(context, R.layout.list_item2, ayatListList);
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.ayatList = ayatListList;


    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ayatList.size();
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView ayaName;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        final View rowView;
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        int width = displayMetrics.widthPixels / 5;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final int screenWidth = metrics.widthPixels;
        final int screenHeight = metrics.heightPixels;

        rowView = inflater.inflate(R.layout.list_item2, null);
        holder.ayaName = (TextView) rowView.findViewById(R.id.list_item);
        // holder.text.setTextDirection(View.TEXT_DIRECTION_RTL);
        holder.ayaName.setTypeface(new Constants(context).getTypeFaceStandard(context));
        holder.ayaName.setText(ayatList.get(position));
        //holder.soraName.setTextSize(textSize);


        return rowView;
    }
}