package com.google.android.gms.example.bannerexample.com.example.customviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

/**
 * Created by sand on 10/14/15.
 */
public class CustomListView extends ArrayAdapter<String> {
    ArrayList<String> headers = new ArrayList<String>();
    ArrayList<String> supHeaders = new ArrayList<String>();
    ArrayList<String> paragraphs = new ArrayList<String>();
    ArrayList<String> hadeth = new ArrayList<String>();
    ArrayList<String> quran = new ArrayList<String>();
    ArrayList<String> parts = new ArrayList<String>();
    ArrayList<String> other = new ArrayList<String>();
    ArrayList<String> lines = new ArrayList<String>();
    ArrayList<String> sourceInfo = new ArrayList<String>();
    ArrayList<String> headersWithoutTashkel = new ArrayList<String>();
    ArrayList<String> linesWithoutTashkel = new ArrayList<String>();
    ArrayList<String> paragraphsWithoutTashkel = new ArrayList<String>();
    ArrayList<String> partsWithoutTashkel = new ArrayList<String>();
    ArrayList<String> quranWithoutTashkel = new ArrayList<String>();
    ArrayList<String> sourceInfoWithoutTashkel = new ArrayList<String>();
    ArrayList<String> hadethWithoutTashkel = new ArrayList<String>();
    ArrayList<String> supHeadersWithoutTashkel = new ArrayList<String>();
    ArrayList<String> otherWithoutTashkel = new ArrayList<String>();


   ArrayList<String> files = new ArrayList<String>();
    Typeface typeface = Typeface.SANS_SERIF;
    Context context;
    float textSize = 20f;
    RelativeLayout r1;
    RelativeLayout r2;
    RelativeLayout r3;
    RelativeLayout r4;
    RelativeLayout r5;
    RelativeLayout r6;
    // LinearLayout layout3;
    ArrayList<String> bookedFiles = new ArrayList<String>();
    ArrayList<String> bookedHeader = new ArrayList<String>();
    ArrayList<String> bookedSupheader = new ArrayList<String>();
    DataBaseHelper db;
    boolean tashkelFlag;
    boolean tashkelFlagClicked = false;

    private static LayoutInflater inflater = null;

    public CustomListView(Context context, ArrayList<String> headers, ArrayList<String> headersWithoutTashkel, ArrayList<String> supHeaders, ArrayList<String> supHeadersWithoutTashkel,
                          ArrayList<String> paragraphs, ArrayList<String> paragraphsWithoutTashkel, ArrayList<String> hadeth, ArrayList<String> hadethWithoutTashkel,
                          ArrayList<String> quran, ArrayList<String> quranWithoutTashkel, ArrayList<String> parts, ArrayList<String> partsWithoutTashkel, ArrayList<String> other, ArrayList<String> otherWithoutTashkel, ArrayList<String> lines,
                          ArrayList<String> linesWithoutTashkel, ArrayList<String> sourceInfo, ArrayList<String> sourceInfoWithoutTashkel, ArrayList<String> files) {
        super(context, R.layout.list_row, headers);
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.headers = headers;
        this.supHeaders = supHeaders;
        this.paragraphs = paragraphs;
        this.hadeth = hadeth;
        this.quran = quran;
        this.parts = parts;
        this.other = other;
        this.lines = lines;
        this.sourceInfo = sourceInfo;
        this.headersWithoutTashkel = headersWithoutTashkel;
        this.supHeadersWithoutTashkel = supHeadersWithoutTashkel;
        this.paragraphsWithoutTashkel = paragraphsWithoutTashkel;
        this.hadethWithoutTashkel = hadethWithoutTashkel;
        this.quranWithoutTashkel = quranWithoutTashkel;
        this.partsWithoutTashkel = partsWithoutTashkel;
        this.otherWithoutTashkel = otherWithoutTashkel;
        this.linesWithoutTashkel = linesWithoutTashkel;
        this.sourceInfoWithoutTashkel = sourceInfoWithoutTashkel;
        this.files = files;
        db = new DataBaseHelper(context);
        typeface = new Constants(context).getTypeFacenaskh(context);


        typeface = new Constants(context).getTypeface();
        textSize = new Constants(context).getTextSize();
        tashkelFlag = new Constants(context).getTashkel();


    }

    public void setTextSize(float size) {
        textSize = size;
        new Constants(context).setTextSize(textSize);

    }

//    public void setTypeFace(Typeface typeFace) {
//
//        this.typeface = typeFace;
//        new Constants(context).setTypeface(typeFace);
//    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return headers.size();
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView header;
        TextView supHeader;
        TextView paragraph;
        ImageButton bookmark;
        ImageButton font;
        ImageButton hideTashkel;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Log.d("listSize", textSize + "");
        Log.d("typeface is", typeface.toString() + "");
        tashkelFlag = new Constants(context).getTashkel();
        Log.d("tashkel is", tashkelFlag + "");

        Log.d("files pos1",files.get(position));

        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels / 3;

        rowView = inflater.inflate(R.layout.list_row, null);
        holder.header = (TextView) rowView.findViewById(R.id.header);
        // holder.text.setTextDirection(View.TEXT_DIRECTION_RTL);
        holder.header.setTypeface(typeface);
        if (tashkelFlag == true) {
            holder.header.setText(headersWithoutTashkel.get(position));
        } else {
            holder.header.setText(headers.get(position));
        }
        holder.header.setTextSize(textSize);

        holder.supHeader = (TextView) rowView.findViewById(R.id.sup_header);
        // holder.text.setTextDirection(View.TEXT_DIRECTION_RTL);
        holder.supHeader.setTypeface(typeface);
        if (tashkelFlag == true) {
            holder.supHeader.setText(supHeadersWithoutTashkel.get(position));

        } else {
            holder.supHeader.setText(supHeaders.get(position));
        }
        holder.supHeader.setTextSize(textSize);

        holder.paragraph = (TextView) rowView.findViewById(R.id.paragraph);
        // holder.text.setTextDirection(View.TEXT_DIRECTION_RTL);
        holder.paragraph.setTypeface(typeface);
        if (tashkelFlag == true) {
            holder.paragraph.setText(paragraphsWithoutTashkel.get(position) + "\n");


        } else {
            holder.paragraph.setText(paragraphs.get(position) + "\n");
        }
        holder.paragraph.setTextSize(textSize);

        if (tashkelFlag == true) {
            try {
                if (paragraphsWithoutTashkel.get(position).contains(hadethWithoutTashkel.get(position))) {

                    Spannable spannable = new SpannableString(paragraphsWithoutTashkel.get(position));
                    int startIndex = paragraphsWithoutTashkel.get(position).indexOf(hadethWithoutTashkel.get(position));
                    int endIndex = startIndex + hadethWithoutTashkel.get(position).toCharArray().length;
                    spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.hadeth)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.paragraph.setText(spannable, TextView.BufferType.SPANNABLE);
                }

                if (paragraphsWithoutTashkel.get(position).contains(quranWithoutTashkel.get(position))) {

                    Spannable spannable = new SpannableString(paragraphsWithoutTashkel.get(position));
                    int startIndex = paragraphsWithoutTashkel.get(position).indexOf(quranWithoutTashkel.get(position));
                    int endIndex = startIndex + quranWithoutTashkel.get(position).toCharArray().length;
                    spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.quran)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.paragraph.setText(spannable, TextView.BufferType.SPANNABLE);
                }


                if (paragraphsWithoutTashkel.get(position).contains(partsWithoutTashkel.get(position))) {

                    Spannable spannable = new SpannableString(paragraphsWithoutTashkel.get(position));
                    int startIndex = paragraphsWithoutTashkel.get(position).indexOf(partsWithoutTashkel.get(position));
                    int endIndex = startIndex + partsWithoutTashkel.get(position).toCharArray().length;
                    spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.part)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.paragraph.setText(spannable, TextView.BufferType.SPANNABLE);
                }

                if (paragraphsWithoutTashkel.get(position).contains(sourceInfoWithoutTashkel.get(position))) {

                    Spannable spannable = new SpannableString(sourceInfoWithoutTashkel.get(position));
                    int startIndex = paragraphsWithoutTashkel.get(position).indexOf(sourceInfoWithoutTashkel.get(position));
                    int endIndex = startIndex + sourceInfoWithoutTashkel.get(position).toCharArray().length;
                    spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.source_info)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.paragraph.setText(spannable, TextView.BufferType.SPANNABLE);
                }
            } catch (Exception e) {


            }
        } else {
            try {
                if (paragraphs.get(position).contains(hadeth.get(position))) {

                    Spannable spannable = new SpannableString(paragraphs.get(position));
                    int startIndex = paragraphs.get(position).indexOf(hadeth.get(position));
                    int endIndex = startIndex + hadeth.get(position).toCharArray().length;
                    spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.hadeth)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.paragraph.setText(spannable, TextView.BufferType.SPANNABLE);
                }

                if (paragraphs.get(position).contains(quran.get(position))) {

                    Spannable spannable = new SpannableString(paragraphs.get(position));
                    int startIndex = paragraphs.get(position).indexOf(quran.get(position));
                    int endIndex = startIndex + quran.get(position).toCharArray().length;
                    spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.quran)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.paragraph.setText(spannable, TextView.BufferType.SPANNABLE);
                }


                if (paragraphs.get(position).contains(parts.get(position))) {

                    Spannable spannable = new SpannableString(paragraphs.get(position));
                    int startIndex = paragraphs.get(position).indexOf(parts.get(position));
                    int endIndex = startIndex + parts.get(position).toCharArray().length;
                    spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.part)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.paragraph.setText(spannable, TextView.BufferType.SPANNABLE);
                }

                if (paragraphs.get(position).contains(sourceInfo.get(position))) {

                    Spannable spannable = new SpannableString(sourceInfo.get(position));
                    int startIndex = paragraphs.get(position).indexOf(sourceInfo.get(position));
                    int endIndex = startIndex + sourceInfo.get(position).toCharArray().length;
                    spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.source_info)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    holder.paragraph.setText(spannable, TextView.BufferType.SPANNABLE);
                }
            } catch (Exception e) {


            }

        }


//        r1 = (RelativeLayout) rowView.findViewById(R.id.RelativeLayout1);
//        r2 = (RelativeLayout) rowView.findViewById(R.id.RelativeLayout2);
//        r3 = (RelativeLayout) rowView.findViewById(R.id.RelativeLayout3);
//        r4 = (RelativeLayout) rowView.findViewById(R.id.RelativeLayout4);
//        r5 = (RelativeLayout) rowView.findViewById(R.id.RelativeLayout5);
//        r6 = (RelativeLayout) rowView.findViewById(R.id.RelativeLayout6);
//        holder.bookmark = (ImageButton) rowView.findViewById(R.id.bookmark);
//
//
//        ViewTreeObserver viewTreeObserver2 =  CustomPagerAdapter.tabLayout
//                .getViewTreeObserver();
//        if (viewTreeObserver2.isAlive()) {
//            viewTreeObserver2
//                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                        @Override
//                        public void onGlobalLayout() {
//                            CustomPagerAdapter.tabLayout.getViewTreeObserver()
//                                    .removeGlobalOnLayoutListener(this);
//                            int allHeight =  CustomPagerAdapter.tabLayout.getHeight();
//                            int allWiDTH =  CustomPagerAdapter.tabLayout.getWidth();
//                            r1.getLayoutParams().height = allHeight;
//                            r2.getLayoutParams().height = allHeight;
//                            r3.getLayoutParams().height = allHeight;
//                            r4.getLayoutParams().height = allHeight;
//                            r5.getLayoutParams().height = allHeight;
//                            r6.getLayoutParams().height = allHeight;
//
//
//                        }
//                    });
//        }
//
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        final int screenWidth = metrics.widthPixels;
//        final int screenHeight = metrics.heightPixels;
//
//       CustomPagerAdapter.tabLayout.setVisibility(View.INVISIBLE);
//
//        holder.paragraph.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if ( CustomPagerAdapter.tabLayout.getVisibility() == View.INVISIBLE) {
//                    CustomPagerAdapter.tabLayout.setVisibility(View.VISIBLE);
//                    CustomPagerAdapter.tabLayout.setX(0);
//                    CustomPagerAdapter.tabLayout.setY(screenHeight-200);
//
//                } else {
//                    CustomPagerAdapter.tabLayout.setVisibility(View.INVISIBLE);
//                }
//                return false;
//            }
//        });
//
//        holder.bookmark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                CustomPagerAdapter.tabLayout.setVisibility(View.INVISIBLE);
//
//                List<FileData> contacts2 = db.getAllContacts();
//                ArrayList<String> savedBookMark=new ArrayList<String>();
//
//                for (FileData cn : contacts2) {
//
//                   savedBookMark.add(cn.getSupHeader());
//                }
//
//
//                String normalValue=new ArabicNormalizer(supHeaders.get(position)).getOutput();
//                if(!savedBookMark.contains(supHeaders.get(position)))
//                {
//                    /**
//                     * CRUD Operations
//                     * */
//                    // Inserting Contacts
//                    Log.d("Insert: ", "Inserting ..");
//                    Log.d("filesbeforinsert", files.toString());
//                    Log.d("fileof pos", files.get(position));
//                    db.addContact(new FileData(files.get(position), headers.get(position), supHeaders.get(position)));
//                    Toast.makeText(context, "Done", Toast.LENGTH_LONG).show();
//
//
//                }
//                else
//                {
//                    Toast.makeText(context, "already saved", Toast.LENGTH_LONG).show();
//
//                }
//
//
//
//
//
////
//
//            }
//        });
//
//
//        holder.font = (ImageButton) rowView.findViewById(R.id.font);
//        holder.font.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomPagerAdapter.tabLayout.setVisibility(View.INVISIBLE);
//                TextFragment.callChangeFont();
//
//
//            }
//        });
//
//        holder.hideTashkel = (ImageButton) rowView.findViewById(R.id.tashkel);
//        holder.hideTashkel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomPagerAdapter.tabLayout.setVisibility(View.INVISIBLE);
//                tashkelFlag = new Constants(context).getTashkel();
//                if (tashkelFlag == false) {
//                    new Constants(context).setTashkel(true);
//                    TextFragment.customListView.notifyDataSetChanged();
//                    MainContentPage.mAdapter.notifyDataSetChanged();
//                } else {
//                    new Constants(context).setTashkel(false);
//                    TextFragment.customListView.notifyDataSetChanged();
//                    MainContentPage.mAdapter.notifyDataSetChanged();
//                }
//
//
//            }
//        });


        return rowView;
    }


}