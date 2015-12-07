package com.google.android.gms.example.bannerexample.com.example.customviews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.example.bannerexample.R;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;

import java.util.ArrayList;

/**
 * Created by sand on 11/17/15.
 */
public class BookmarListView extends ArrayAdapter<String> {

    static Activity context;
    ArrayList<String> ayatList = new ArrayList<String>();
    ArrayList<String> soraLists=new ArrayList<String>();
    ArrayList<String> ids=new ArrayList<String>();
    Typeface typeface=Typeface.SANS_SERIF;
    float textSize = 20f;



    private static LayoutInflater inflater = null;

    public BookmarListView(Activity context, ArrayList<String> ayatListList,ArrayList<String> soraLists,ArrayList<String>ids) {
        super(context, R.layout.bookmark_item, ayatListList);
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.ayatList = ayatListList;
        this.soraLists=soraLists;
        this.ids=ids;
        typeface=new Constants(context).getTypeface();
        textSize=new Constants(context).getTextSize();


    }

    public void setTextSize(float size){
        textSize = size;
        new Constants(context).setTextSize(textSize);
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
        TextView soraName;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        typeface=new Constants(context).getTypeface();
        textSize=new Constants(context).getTextSize();
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        final View rowView;
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        int width = displayMetrics.widthPixels / 5;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final int screenWidth = metrics.widthPixels;
        final int screenHeight = metrics.heightPixels;

        rowView = inflater.inflate(R.layout.bookmark_item, null);
        holder.ayaName = (TextView) rowView.findViewById(R.id.text1);
        // holder.text.setTextDirection(View.TEXT_DIRECTION_RTL);
        holder.ayaName.setTypeface(typeface);
        holder.ayaName.setText(ayatList.get(position));
        //holder.soraName.setTextSize(textSize);
        holder.ayaName.setTextSize(textSize);

        holder.soraName = (TextView) rowView.findViewById(R.id.text2);
        // holder.text.setTextDirection(View.TEXT_DIRECTION_RTL);
        holder.soraName.setTypeface(typeface);
        holder.soraName.setText(soraLists.get(position));
        holder.soraName.setTextSize(textSize);


        return rowView;
    }
}