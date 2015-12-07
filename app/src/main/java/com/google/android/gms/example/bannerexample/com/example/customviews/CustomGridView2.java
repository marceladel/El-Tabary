package com.google.android.gms.example.bannerexample.com.example.customviews;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.google.android.gms.example.bannerexample.MainActivity;
import com.google.android.gms.example.bannerexample.R;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;

import java.util.ArrayList;

/**
 * Created by sand on 10/14/15.
 */
public class CustomGridView2 extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> headersNames=new ArrayList<String>();
    private static LayoutInflater inflater=null;
    public CustomGridView2(Context context,ArrayList<String> headers) {
        super(context, R.layout.grid_row2, headers);
        headersNames=headers;
        this.context=context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return headersNames.size();
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        Button text;
        LinearLayout textLayout;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels/5;

        rowView = inflater.inflate(R.layout.grid_row2, null);
        holder.text=(Button) rowView.findViewById(R.id.button_text);
        // holder.text.setTextDirection(View.TEXT_DIRECTION_RTL);
        //holder.text.setTypeface(new Constants().getTypeFace(context));
        holder.text.setText(headersNames.get(position));
        holder.text.getLayoutParams().width=width;

        holder.textLayout=(LinearLayout)rowView.findViewById(R.id.grid_layout);
        holder.textLayout.getLayoutParams().width=width;


//        rowView.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
//            }
//        });

        return rowView;
    }

}