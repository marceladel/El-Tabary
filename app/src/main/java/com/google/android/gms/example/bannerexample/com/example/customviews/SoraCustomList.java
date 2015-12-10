package com.google.android.gms.example.bannerexample.com.example.customviews;

/**
 * Created by sand on 11/16/15.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.sax.RootElement;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.example.bannerexample.MainContentPage;
import com.google.android.gms.example.bannerexample.R;
import com.google.android.gms.example.bannerexample.com.example.constant.ArabicNormalizer;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.constant.StoreData;
import com.google.android.gms.example.bannerexample.com.example.parsing.Data;
import com.google.android.gms.example.bannerexample.com.example.parsing.InfoArray;
import com.google.android.gms.example.bannerexample.com.example.parsing.InfoData;
import com.google.android.gms.example.bannerexample.com.example.parsing.SearchData;
import com.google.android.gms.example.bannerexample.com.example.parsing.SearchDataArrayElements;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.android.gms.example.bannerexample.R.layout.main_layout;
import static com.google.android.gms.example.bannerexample.R.layout.search_item;

/**
 * Created by sand on 10/14/15.
 */
public class SoraCustomList extends ArrayAdapter<String> {

    static Activity context;
    ArrayList<String> soraList = new ArrayList<String>();
    ArrayList<String> soraNumbers = new ArrayList<String>();
    ArrayList<String> ayat = new ArrayList<String>();
    float textSize = 25f;
    String value;
    ListView listView;
    Typeface typeface = Typeface.SANS_SERIF;
    ArrayList<String> files = new ArrayList<String>();
    LinearLayout layoutitem;
    LinearLayout layoutimage;
    // LinearLayout numberLayout;
    LinearLayout numberlayout;
    int allHeight;
    int height;
    ArrayList<String> newAyat = new ArrayList<String>();
    ArrayList<String> newData = new ArrayList<String>();
    View layout;
    ArrayList<SearchDataArrayElements> searchElemnts;
    String fileContent = null;
    ArrayList<String> ayatList = new ArrayList<String>();
    ArrayList<String> soraNamesList = new ArrayList<String>();
    ArrayList<String> filesList = new ArrayList<String>();
    ArrayList<String> ArabicNumricAyatList = new ArrayList<String>();
    String editTextValue;
    String neweditTextValue;
    boolean flag = false;
    String filename;
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> soraOrder = new ArrayList<String>();
    ArrayList<String> soraName = new ArrayList<String>();
    ArrayList<String> elnzolOrder = new ArrayList<String>();
    ArrayList<String> ayatNumber = new ArrayList<String>();
    ArrayList<String> wordNumber = new ArrayList<String>();
    ArrayList<String> charNumber = new ArrayList<String>();
    ArrayList<String> placeBirth = new ArrayList<String>();
    ArrayList<Integer> ayaNumbers = new ArrayList<Integer>();
    boolean matcher = false;
    ArrayList<String> sortedArray = new ArrayList<String>();
    ArrayList<String> ayaNumbers2 = new ArrayList<String>();
    ListAdapter arrayAdapter;
    ArrayList<String> filesSorted = new ArrayList<String>();
    ProgressBar progressbar;
    boolean flagScrollClicked = false;
    boolean goFlag=false;
    boolean listItemClicked=false;

    int start, end;

    private static LayoutInflater inflater = null;

    public SoraCustomList(Activity context, ArrayList<String> soraList, ArrayList<String> soraNumbers, ArrayList<String> ids) {
        super(context, R.layout.list_item, soraList);
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.soraList = soraList;
        this.soraNumbers = soraNumbers;
        typeface = new StoreData(context).getTypeFacenaskh(context);
        this.ids = ids;


    }


    public void setTextSize(float size) {
        textSize = size;
    }

    public void setTypeFace(Typeface typeFace) {
        this.typeface = typeFace;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return soraList.size();
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView soraNumber;
        public TextView soraName;
        LinearLayout relativelayout;
        ImageButton info;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();
        final View rowView;
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        int width = displayMetrics.widthPixels / 5;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final int screenWidth = metrics.widthPixels;
        final int screenHeight = metrics.heightPixels;

        rowView = inflater.inflate(R.layout.list_item, null);

        layoutimage = (LinearLayout) rowView.findViewById(R.id.layoutimage);
        layoutitem = (LinearLayout) rowView.findViewById(R.id.content);
        numberlayout = (LinearLayout) rowView.findViewById(R.id.numberlayout);
        holder.relativelayout = (LinearLayout) rowView.findViewById(R.id.layout);
//            ViewTreeObserver viewTreeObserver3 = layoutitem
//                    .getViewTreeObserver();
//            if (viewTreeObserver3.isAlive()) {
//                viewTreeObserver3
//                        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                            @Override
//                            public void onGlobalLayout() {
//                                height = layoutitem.getHeight();
//                                Log.d("height is", height + "");
//                                int h=height;
//
//
//
//
//
//                            }
//                        });
//            }

        layoutimage.setLayoutParams(new LinearLayout.LayoutParams(screenHeight / 8, screenHeight / 8));
        layoutitem.getLayoutParams().height = screenHeight / 8;
        numberlayout.setLayoutParams(new LinearLayout.LayoutParams(screenHeight / 8, screenHeight / 8));

//
//        layoutitem.post(new Runnable() {
//            public void run() {
//
////                layoutimage.getLayoutParams().width=height;
////                layoutimage.getLayoutParams().height=height;
////                numberlayout.getLayoutParams().width=height;
////                numberlayout.getLayoutParams().height=height;
//
//
//            }
//        });
//
        holder.soraName = (TextView) rowView.findViewById(R.id.title);
        // holder.text.setTextDirection(View.TEXT_DIRECTION_RTL);
        holder.soraName.setTypeface(typeface);
        holder.soraName.setText(soraList.get(position));
        holder.soraName.setTextSize(textSize);

        holder.soraNumber = (TextView) rowView.findViewById(R.id.number);
        // holder.text.setTextDirection(View.TEXT_DIRECTION_RTL);
        holder.soraNumber.setTypeface(new StoreData(context).getTypeFaceStandard(context));
        holder.soraNumber.setText(soraNumbers.get(position));
        holder.soraNumber.setTextSize(textSize);
        holder.relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LinearLayout viewGroup = new LinearLayout(context);

                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                layout = layoutInflater.inflate(R.layout.popup_layout,
                        viewGroup);
                final PopupWindow popup = new PopupWindow(layout, screenWidth,
                        screenHeight, true);
                popup.setBackgroundDrawable(context.getResources().getDrawable(
                        R.drawable.popup_drawable));

                popup.setAnimationStyle(R.style.AnimationPopup);
                popup.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);
                TextView soraName = (TextView) layout.findViewById(R.id.soraname);
                TextView textView = (TextView) layout.findViewById(R.id.textview);
                textView.setTypeface(new StoreData(context).getTypeFacenaskh(context));
//                progressbar = (ProgressBar) layout.findViewById(R.id.loading);
//                progressbar.setIndeterminateDrawable(context.getResources().getDrawable(
//                        R.drawable.dialog_background));

                final EditText editText = (EditText) layout.findViewById(R.id.edittext);
                final ImageButton go = (ImageButton) layout.findViewById(R.id.go);
                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(goFlag==false) {
                            Log.d("inside click","msg");
                            goFlag=true;
                            editTextValue = editText.getText().toString();
                            if (editTextValue.length() >= 1) {
                                flag = true;
                                neweditTextValue = editTextValue.replaceAll("0", "٠").replaceAll("1", "١").replaceAll("2", "٢").replaceAll("3", "٣").replaceAll("4", "٤").replaceAll("5", "٥")
                                        .replaceAll("6", "٦").replaceAll("7", "٧").replaceAll("8", "٨").replaceAll("9", "٩");
                                for (int i = 0; i < ArabicNumricAyatList.size(); i++) {
                                    if (ArabicNumricAyatList.get(i).matches(".*\\b" + neweditTextValue + "\\b.*")) {
                                        flag = true;
                                        Log.d("count", "msg");
                                        filename = files.get(i);
                                        //  new StoreData(context).setFiles(files);
                                        Intent gotoMainContent = new Intent(context, MainContentPage.class);
                                        gotoMainContent.putStringArrayListExtra("files", files);
                                        gotoMainContent.putExtra("pos", i);
                                        gotoMainContent.putExtra("activityflag", true);
                                        context.startActivity(gotoMainContent);
                                        goFlag=false;
                                    }
                                }
                            } else {
                                Toast.makeText(context, "رجاءً إدخل رقم الأية", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                value = soraList.get(position);
                value = "سورة" + " " + new ArabicNormalizer(value).getOutput();
                Log.d("valueee", value);
                soraName.setText("ايات سورة " + soraList.get(position));
                soraName.setTypeface(new StoreData(context).getTypeFaceStandard(context));

                try {
                    fileContent = new Constants(context).readAssets(context, "eltabary", "search_file.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                SearchData searchData = gson.fromJson(fileContent, SearchData.class);
                searchElemnts = searchData.data;

                start = 0;
                end = start + 7;

                newData.clear();
                ayat.clear();
                files.clear();
                ayatList.clear();
                soraNamesList.clear();
                filesList.clear();
                ArabicNumricAyatList.clear();
                ayaNumbers.clear();
                ayaNumbers2.clear();
                sortedArray.clear();

                SearchTask object = new SearchTask();
                object.execute();
                listView = (ListView) layout.findViewById(R.id.list);
//                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//                    @Override
//                    public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//
//                    }
//
//                    @Override
//                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                        if (flagScrollClicked == true) {
//                            int position = firstVisibleItem + visibleItemCount;
//                            int limit = totalItemCount;
//                            int totalItems = searchElemnts.size();
//
//                            if (position >= limit && totalItemCount > 0
//                                    && ScrollMaxCapacity.contains("true")) {
//
//                                Log.i("TOTAL WORLD ITEMS", String.valueOf(totalItems));
//                                Log.i("LIMIT :::::::::::: ", String.valueOf(limit));
//                                Log.i("POSITION ::::::::::::", String.valueOf(position));
//                                Log.i("REFRESHING :::::::::::::::",
//                                        String.valueOf(layout.isRefreshing()));
//
//                                object3 = new ScrollPoststTask();
//                                object3.execute();
//                                Log.d("ScroolDown", "msg");
//                            }
//
//                    }
//                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(listItemClicked==false) {
                            Log.d("inside click2","msg");
                            listItemClicked=true;
                            filename = files.get(position);
                            // new StoreData(context).setFiles(files);
                            //  editText.setText(String.valueOf(position+1));
                            Intent gotoMainContent = new Intent(context, MainContentPage.class);
                            gotoMainContent.putStringArrayListExtra("files", files);
                            gotoMainContent.putExtra("pos", position);
                            gotoMainContent.putExtra("activityflag", true);
                            context.startActivity(gotoMainContent);
                            listItemClicked=false;
                        }
                    }
                });

            }
        });

        holder.info = (ImageButton) rowView.findViewById(R.id.info);
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LinearLayout viewGroup = new LinearLayout(context);

                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                layout = layoutInflater.inflate(R.layout.info_popup,
                        viewGroup);
                final PopupWindow popup = new PopupWindow(layout, screenWidth,
                        screenHeight, true);
                popup.setBackgroundDrawable(context.getResources().getDrawable(
                        R.drawable.popup_drawable));

                popup.setAnimationStyle(R.style.AnimationPopup);
                popup.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);

                TextView tartebEslora = (TextView) layout.findViewById(R.id.txt1);
                tartebEslora.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView tartebEsloraValue = (TextView) layout.findViewById(R.id.txt2);
                tartebEsloraValue.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView soraName2 = (TextView) layout.findViewById(R.id.txt3);
                soraName2.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView soraNameValue2 = (TextView) layout.findViewById(R.id.txt4);
                soraNameValue2.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView tartebElnzol = (TextView) layout.findViewById(R.id.txt5);
                tartebElnzol.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView tartebElnzolValue = (TextView) layout.findViewById(R.id.txt6);
                tartebElnzolValue.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView ayatNumber2 = (TextView) layout.findViewById(R.id.txt7);
                ayatNumber2.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView ayatNumberValue = (TextView) layout.findViewById(R.id.txt8);
                ayatNumberValue.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView wordsNumber = (TextView) layout.findViewById(R.id.txt9);
                wordsNumber.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView wordsNumberValue = (TextView) layout.findViewById(R.id.txt10);
                wordsNumberValue.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView charNumber2 = (TextView) layout.findViewById(R.id.txt11);
                charNumber2.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView charNumberValue = (TextView) layout.findViewById(R.id.txt12);
                charNumberValue.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView placeBirth2 = (TextView) layout.findViewById(R.id.txt13);
                placeBirth2.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                TextView placeBirthValue = (TextView) layout.findViewById(R.id.txt14);
                placeBirthValue.setTypeface(new StoreData(context).getTypeFacenaskh(context));
                parseInfoFile(ids.get(position));

                tartebEsloraValue.setText(soraOrder.get(position));
                soraNameValue2.setText(soraName.get(position));
                tartebElnzolValue.setText(elnzolOrder.get(position));
                ayatNumberValue.setText(ayatNumber.get(position));
                wordsNumberValue.setText(wordNumber.get(position));
                charNumberValue.setText(charNumber.get(position));
                placeBirthValue.setText(placeBirth.get(position));


            }
        });


        return rowView;
    }


    public void parseInfoFile(String id) {
        String infoFileContent = new Constants(context).readAssets(context, "eltabary", "info.txt");
        Log.d("infoContent", infoFileContent);

        Gson gson = new Gson();
        InfoArray data = gson.fromJson(infoFileContent, InfoArray.class);
        ArrayList<InfoData> infoData = data.info;

        for (int i = 0; i < infoData.size(); i++) {
            soraOrder.add(new Constants(context).arabicNumaricCode(infoData.get(i).soraOrder));
            soraName.add(infoData.get(i).soraName);
            elnzolOrder.add(new Constants(context).arabicNumaricCode(infoData.get(i).tartebElnzol));
            ayatNumber.add(new Constants(context).arabicNumaricCode(infoData.get(i).ayatNumber));
            wordNumber.add(new Constants(context).arabicNumaricCode(infoData.get(i).wordsNumber));
            charNumber.add(new Constants(context).arabicNumaricCode(infoData.get(i).charNumber));
            placeBirth.add(infoData.get(i).mkanElnzol);
        }

    }

    private class SearchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

           // progressbar.setVisibility(View.VISIBLE);
        }

        //
        @Override
        protected String doInBackground(String... params) {
            Log.d("start is", start + "");
            Log.d("end is", end + "");

            for (int i = 0; i < searchElemnts.size(); i++) {
                ayatList.add(searchElemnts.get(i).aya);
                soraNamesList.add(searchElemnts.get(i).sora);
                filesList.add(searchElemnts.get(i).fileName);
            }


            String ayaValue=null;

            Log.d("soraList", soraNamesList.toString());
            for (int i = 0; i < soraNamesList.size(); i++) {
                if (soraNamesList.get(i).equals(value)) {
                    //  Log.d("exist", "msg");
                    //  Log.d("pos", i + "");
                    ayaValue = ayatList.get(i);
                    if (ayaValue.length() > 35) {
                        ayaValue = ayaValue.substring(0, 35) + " ...";
                    } else {
                        ayaValue = ayatList.get(i);
                    }

                    Log.d("value is",value);
                    if (!ayat.contains(ayaValue)) {
                        Log.d("value in","msg");
                        ayat.add(ayaValue);
                        files.add(filesList.get(i));


                    }

                }
            }
//            String value2 = null;
//            for (int i = 0; i < ayat.size(); i++) {
//                value = ayat.get(i);
//                if (value.length() > 35) {
//                    value = value.substring(0, 35) + " ...";
//                } else {
//                    value = ayat.get(i);
//                }
//                newData.add(value);
//            }


            String value;

            for (int i = 0; i < ayat.size(); i++) {
                value = ayat.get(i);
                String value3 = value.replaceAll("0", "٠").replaceAll("1", "١").replaceAll("2", "٢").replaceAll("3", "٣").replaceAll("4", "٤").replaceAll("5", "٥")
                        .replaceAll("6", "٦").replaceAll("7", "٧").replaceAll("8", "٨").replaceAll("9", "٩");
                ArabicNumricAyatList.add(value3);

            }
//            ayaNumbers = new Constants(context).extractNumbers(ArabicNumricAyatList);
//            Collections.sort(ayaNumbers);
//            Collections.reverse(ayaNumbers);
//            //ayaNumbers=new Constants().arabicNumaricCode2(ayaNumbers);
//            for (int i = 0; i < ayaNumbers.size(); i++) {
//                ayaNumbers2.add(new Constants(context).arabicNumaricCode(ayaNumbers.get(i).toString()));
//            }
//            Log.d("numbers are", ayaNumbers2.toString());
//            String valueReturned = null;
//            for (int i = 0; i < ayaNumbers2.size(); i++) {
//                Log.d("value is", ayaNumbers2.get(i));
//
//                for (int j = 0; j < ArabicNumricAyatList.size(); j++) {
//
//                    if (ArabicNumricAyatList.get(j).matches(".*\\b" + ayaNumbers2.get(i) + "\\b.*")) {
//                        Log.d("MATCHER IS", ArabicNumricAyatList.get(j));
//                        sortedArray.add(0, ArabicNumricAyatList.get(j));
//                        Log.d("aya", ArabicNumricAyatList.get(j));
//                        Log.d("file is", files.get(j));
//
//                        filesSorted.add(0, files.get(j));
//
//                    }
//                }
//                matcher = false;
//
//            }
//
//            Log.d("sortedArray", sortedArray.toString());


//            for (int i=0;i<ayat.size();i++)
//            {
//                value2=String.valueOf(i);
//                value2=value2.replace("0","٠").replace("1","١").replace("2", "٢").replace("3","٣").replace("4", "٤").replace("5", "٥")
//                        .replace("6", "٦").replace("7", "٧").replace("8", "٨").replace("9", "٩");
//                newAyat.add(value2);
//            }
//
//            Log.d("ayat", ayat.toString());

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            arrayAdapter = new ListAdapter(context, ArabicNumricAyatList);
            listView.setAdapter(arrayAdapter);
            //progressbar.setVisibility(View.INVISIBLE);


        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class SearchTaskScroll extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            newData.clear();
            ayat.clear();
            files.clear();
            ayatList.clear();
            soraNamesList.clear();
            filesList.clear();
            ArabicNumricAyatList.clear();
            ayaNumbers.clear();
            ayaNumbers2.clear();
            sortedArray.clear();
            progressbar.setVisibility(View.VISIBLE);
        }

        //
        @Override
        protected String doInBackground(String... params) {

            for (int i = 0; i < searchElemnts.size(); i++) {
                ayatList.add(searchElemnts.get(i).aya);
                soraNamesList.add(searchElemnts.get(i).sora);
                filesList.add(searchElemnts.get(i).fileName);
            }


            Log.d("soraList", soraNamesList.toString());
            for (int i = 0; i < soraNamesList.size(); i++) {
                if (soraNamesList.get(i).equals(value)) {
                    //  Log.d("exist", "msg");
                    //  Log.d("pos", i + "");
                    if (!ayat.contains(ayatList.get(i))) {
                        ayat.add(ayatList.get(i));
                        files.add(filesList.get(i));
                        //   Log.d("filename", filesList.get(i) + "");

                    }

                }
            }
            String value2 = null;
            for (int i = 0; i < ayat.size(); i++) {
                value = ayat.get(i);
                if (value.length() > 35) {
                    value = value.substring(0, 35) + " ...";
                } else {
                    value = ayat.get(i);
                }
                newData.add(value);
            }


            String value;

            for (int i = 0; i < newData.size(); i++) {
                value = newData.get(i);
                String value3 = value.replaceAll("0", "٠").replaceAll("1", "١").replaceAll("2", "٢").replaceAll("3", "٣").replaceAll("4", "٤").replaceAll("5", "٥")
                        .replaceAll("6", "٦").replaceAll("7", "٧").replaceAll("8", "٨").replaceAll("9", "٩");
                ArabicNumricAyatList.add(value3);

            }
            ayaNumbers = new Constants(context).extractNumbers(ArabicNumricAyatList);
            Collections.sort(ayaNumbers);
            Collections.reverse(ayaNumbers);
            //ayaNumbers=new Constants().arabicNumaricCode2(ayaNumbers);
            for (int i = 0; i < ayaNumbers.size(); i++) {
                ayaNumbers2.add(new Constants(context).arabicNumaricCode(ayaNumbers.get(i).toString()));
            }
            Log.d("numbers are", ayaNumbers2.toString());
            String valueReturned = null;
            for (int i = 0; i < ayaNumbers2.size(); i++) {
                Log.d("value is", ayaNumbers2.get(i));

                for (int j = 0; j < ArabicNumricAyatList.size(); j++) {

                    if (ArabicNumricAyatList.get(j).matches(".*\\b" + ayaNumbers2.get(i) + "\\b.*")) {
                        Log.d("MATCHER IS", ArabicNumricAyatList.get(j));
                        sortedArray.add(0, ArabicNumricAyatList.get(j));
                        Log.d("aya", ArabicNumricAyatList.get(j));
                        Log.d("file is", files.get(j));

                        filesSorted.add(0, files.get(j));

                    }
                }
                matcher = false;

            }

            Log.d("sortedArray", sortedArray.toString());


//            for (int i=0;i<ayat.size();i++)
//            {
//                value2=String.valueOf(i);
//                value2=value2.replace("0","٠").replace("1","١").replace("2", "٢").replace("3","٣").replace("4", "٤").replace("5", "٥")
//                        .replace("6", "٦").replace("7", "٧").replace("8", "٨").replace("9", "٩");
//                newAyat.add(value2);
//            }
//
//            Log.d("ayat", ayat.toString());

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

//            arrayAdapter = new ListAdapter(context, sortedArray);
//            listView.setAdapter(arrayAdapter);
            progressbar.setVisibility(View.INVISIBLE);
            arrayAdapter.notifyDataSetChanged();


        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
//    public String getItemPosition(String value) {
//        String foundString = null;
//        for (int i = 0; i < ArabicNumricAyatList.size(); i++) {
//
//            Log.d("value is", value);
//            Log.d("list value", ArabicNumricAyatList.get(i));
//            if (ArabicNumricAyatList.get(i).contains(value) ) {
//                foundString = ArabicNumricAyatList.get(i);
//                Log.d("insideCond", foundString);
//
//            }
//
//        }
//
//        return foundString;
//
//    }


}