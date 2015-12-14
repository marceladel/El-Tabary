package com.google.android.gms.example.bannerexample;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.google.android.gms.example.bannerexample.com.android.tabary.database.DataBaseHelper;
import com.google.android.gms.example.bannerexample.com.android.tabary.database.FileData;
import com.google.android.gms.example.bannerexample.com.example.constant.ArabicNormalizer;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.constant.StoreData;
import com.google.android.gms.example.bannerexample.com.example.customviews.CustomListView;
import com.google.android.gms.example.bannerexample.com.example.customviews.SearchAdapter;
import com.google.android.gms.example.bannerexample.com.example.parsing.SearchData;
import com.google.android.gms.example.bannerexample.com.example.parsing.SearchDataArrayElements;
import com.google.android.gms.example.bannerexample.facebook_share.FaceBookShare;
import com.google.gson.Gson;

public class TextFragment extends Fragment {
    int fragVal;
    public static CustomListView customListView;
    public static ArrayList<CustomListView> lists;
    String fileContent;
    ArrayList<String> files;
    ArrayList<String> hadeth;
    ArrayList<String> headers;
    ArrayList<String> lines;
    ArrayList<String> newAyatList;
    ArrayList<String> newFiles;
    ArrayList<String> newParagraphs;
    ArrayList<String> other;
    ArrayList<String> paragraphs;
    ArrayList<String> parts;
    ArrayList<String> quran;
    ArrayList<String> sourceInfo;
    ArrayList<String> supHeaders;
    public static TextFragment context;
    public static ListView listView;
    ArrayList<String> headersWithoutTashkel;
    ArrayList<String> linesWithoutTashkel;
    ArrayList<String> otherWithoutTashkel;
    ArrayList<String> paragraphsWithoutTashkel;
    ArrayList<String> partsWithoutTashkel;
    ArrayList<String> quranWithoutTashkel;
    ArrayList<String> sourceInfoWithoutTashkel;
    ArrayList<String> hadethWithoutTashkel;
    ArrayList<String> supHeadersWithoutTashkel;
    RelativeLayout r1;
    RelativeLayout r2;
    RelativeLayout r3;
    RelativeLayout r4;
    RelativeLayout r5;
    RelativeLayout r6;
    DataBaseHelper db;
    LinearLayout tabLayout;
    ProgressBar progressbar;
    boolean sharedFlag=false;
    boolean longClick=false;
    private static final List<String> PERMISSIONS = Arrays
            .asList("publish_actions");
    Session.StatusCallback statusCallback;
    private UiLifecycleHelper uiHelper;
    List<String> declinedPerm=new ArrayList<String>();



    static TextFragment init(int val) {
        TextFragment truitonFrag = new TextFragment();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonFrag.setArguments(args);
        Log.d("initFragment", "msg");
        return truitonFrag;



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        uiHelper = new UiLifecycleHelper(getActivity(), statusCallback);
        uiHelper.onCreate(savedInstanceState);
        View layoutView = inflater.inflate(R.layout.pager_item, container,
                false);

        setRetainInstance(true);
        View tv = layoutView.findViewById(R.id.text);

        listView = (ListView) layoutView.findViewById(R.id.list);
        boolean tashkelFlag = new StoreData(getActivity()).getTashkel();
        context = this;
        db = new DataBaseHelper(getActivity());
        progressbar = (ProgressBar) layoutView.findViewById(R.id.loading);
        progressbar.setIndeterminateDrawable(context.getResources().getDrawable(
                R.drawable.dialog_background));
        progressbar.setVisibility(View.INVISIBLE);

//        getDataTask object=new getDataTask();
//        object.execute();
//        Thread background = new Thread(new Runnable() {
//
//            public void run() {
//
//                readTxt(fragVal);
//
//            }
//        });
//        listView.setAdapter(customListView);
        readTxt(fragVal);
        listView.setAdapter(customListView);

        tabLayout = (LinearLayout) layoutView.findViewById(R.id.layout4);
        r1 = (RelativeLayout) layoutView.findViewById(R.id.RelativeLayout1);
        r2 = (RelativeLayout) layoutView.findViewById(R.id.RelativeLayout2);
        r3 = (RelativeLayout) layoutView.findViewById(R.id.RelativeLayout3);
        r4 = (RelativeLayout) layoutView.findViewById(R.id.RelativeLayout4);
        r5 = (RelativeLayout) layoutView.findViewById(R.id.RelativeLayout5);
        r6 = (RelativeLayout) layoutView.findViewById(R.id.RelativeLayout6);
       final ImageButton bookmark = (ImageButton) layoutView.findViewById(R.id.bookmark);

        final RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        ViewTreeObserver viewTreeObserver2 = tabLayout
                .getViewTreeObserver();
        if (viewTreeObserver2.isAlive()) {
            viewTreeObserver2
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            tabLayout.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                            int allHeight = tabLayout.getHeight();
                            int allWiDTH = tabLayout.getWidth();
                            r1.getLayoutParams().height = allHeight;
                            r2.getLayoutParams().height = allHeight;
                            r3.getLayoutParams().height = allHeight;
                            r4.getLayoutParams().height = allHeight;
                            r5.getLayoutParams().height = allHeight;
                            r6.getLayoutParams().height = allHeight;


                        }
                    });
        }

        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        final int screenWidth = metrics.widthPixels;
        final int screenHeight = metrics.heightPixels;

        tabLayout.setVisibility(View.INVISIBLE);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (longClick == false) {
                    longClick = true;
                    Log.d("inside onlongd click", "msg");
//                if (tabLayout.getVisibility() == View.INVISIBLE) {
                    tabLayout.setX(0);
                    tabLayout.setY(screenWidth);
                    tabLayout.setVisibility(View.VISIBLE);

//                    params.leftMargin=10;
//                    params.rightMargin=10;
                    // tabLayout.setLayoutParams(params);
                } else {
                    longClick = false;
                    tabLayout.setVisibility(View.INVISIBLE);

                }

//                } else {
//                    tabLayout.setVisibility(View.INVISIBLE);
//                }

                bookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        tabLayout.setVisibility(View.INVISIBLE);

                        List<FileData> contacts2 = db.getAllContacts();
                        ArrayList<String> savedBookMark = new ArrayList<String>();

                        for (FileData cn : contacts2) {

                            savedBookMark.add(cn.getSupHeader());
                        }
                        Log.d("size of supheaders", supHeaders.size() + "" + "header is" + supHeaders.get(0));
//                        Log.d("currentPos",MainContentPage.mPager.getCurrentItem()+"");

                        //   String normalValue = new ArabicNormalizer(supHeaders.get(MainContentPage.mPager.getCurrentItem())).getOutput();
                        if (!savedBookMark.contains(supHeaders.get(0))) {
                            /**
                             * CRUD Operations
                             * */
                            // Inserting Contacts
                            Log.d("Insert: ", "Inserting ..");
                            Log.d("filesbeforinsert", newFiles.toString());
                            Log.d("fileof pos", newFiles.get(0));
                            db.addContact(new FileData(newFiles.get(0), headers.get(0), supHeaders.get(0)));
                            Toast.makeText(getActivity(), "تم", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(getActivity(), "لقد تم الحفظ من قبل", Toast.LENGTH_LONG).show();

                        }


//

                    }
                });
                return false;
            }
        });




        ImageButton font = (ImageButton) layoutView.findViewById(R.id.font);
        font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLayout.setVisibility(View.INVISIBLE);
                callChangeFont();


            }
        });

        ImageButton share=(ImageButton) layoutView.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLayout.setVisibility(View.INVISIBLE);
                String accessToken=new StoreData(getActivity()).getAccessToken();
                Session   session;
                if (accessToken != "") {
                    Log.d("accessToken",accessToken);

                    try {
                        AccessToken accesstoken = AccessToken
                                .createFromExistingAccessToken(accessToken, null,
                                        null, null, null);
                     session = Session.openActiveSessionWithAccessToken(
                             getActivity(), accesstoken, statusCallback);
                    } catch (Exception e) {
                        // TODO: handle exception
                        session = Session.getActiveSession();
                    }

                } else {
                    session = Session.getActiveSession();
                }


                if (session != null && session.isOpened()) {
                    postStatusMessage(paragraphs.get(0),session);


                }

                else {

                    Intent gotoShare = new Intent(getActivity(), FaceBookShare.class);
                    gotoShare.putExtra("data", paragraphs.get(0));
                    startActivity(gotoShare);
                }
            }
        });

        ImageButton hideTashkel = (ImageButton) layoutView.findViewById(R.id.tashkel);
        hideTashkel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean tashkelFlag = new StoreData(getActivity()).getTashkel();

                tabLayout.setVisibility(View.INVISIBLE);
                tashkelFlag = new StoreData(getActivity()).getTashkel();
                if (tashkelFlag == false) {
                    new StoreData(getActivity()).setTashkel(true);
                    TextFragment.customListView.notifyDataSetChanged();
                    MainContentPage.mAdapter.notifyDataSetChanged();
                } else {
                    new StoreData(getActivity()).setTashkel(false);
                    TextFragment.customListView.notifyDataSetChanged();
                    MainContentPage.mAdapter.notifyDataSetChanged();
                }


            }
        });




        return layoutView;
    }

    public static void callChangeFont() {
        context.changeFont();
    }

    public void changeFont() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        View layout = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.change_font_popup, null);
        final PopupWindow popup = new PopupWindow(layout, screenWidth, screenHeight, true);
        popup.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_drawable));
        popup.setAnimationStyle(R.style.AnimationPopup);
        popup.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);
        ((TextView) layout.findViewById(R.id.txt1)).setTypeface(new StoreData(getActivity()).getTypeFaceStandard(getActivity()));
        TextView txt2 = (TextView) layout.findViewById(R.id.txt2);
        txt2.setTypeface(new StoreData(getActivity()).getTypeFaceThoulth(getActivity()));
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClickText0", "msg");
                popup.dismiss();
                new StoreData(getActivity()).setTypeface(1);
                TextFragment.customListView.notifyDataSetChanged();
                MainContentPage.mAdapter.notifyDataSetChanged();



            }
        });
        TextView txt3 = (TextView) layout.findViewById(R.id.txt3);
        txt3.setTypeface(new StoreData(getActivity()).getTypeFacediwany(getActivity()));
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClickText1", "msg");
                popup.dismiss();
                new StoreData(getActivity()).setTypeface(2);
                TextFragment.customListView.notifyDataSetChanged();
                MainContentPage.mAdapter.notifyDataSetChanged();



            }
        });
        TextView txt4 = (TextView) layout.findViewById(R.id.txt4);
        txt4.setTypeface(new StoreData(getActivity()).getTypeFacenaskh(getActivity()));
        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClickText2", "msg");

                popup.dismiss();
                new StoreData(getActivity()).setTypeface(3);
                TextFragment.customListView.notifyDataSetChanged();
                MainContentPage.mAdapter.notifyDataSetChanged();



            }
        });
    }

    public void readTxt(int pageNumber) {
        Log.d("read txt","msg");

        if(headers!=null) {
            headers.clear();
            supHeaders.clear();
            newFiles.clear();
        }

        files = new StoreData(getActivity()).getFiles();
        Log.d("files is", files.toString());
        fileContent = new Constants(getActivity()).readAssets(MainContentPage.getAppContext(), "eltabary", this.files.get(pageNumber));
        headers = new Constants(getActivity()).parseHTML(this.fileContent, "h2");
        headersWithoutTashkel = new Constants(getActivity()).NormalizeCode(headers);
        supHeaders = new Constants(getActivity()).parseHTML(this.fileContent, "h3");
        paragraphs = new Constants(getActivity()).parseHTML(this.fileContent, "p");
        hadeth = new Constants(getActivity()).parseHTML(this.fileContent, "hadith");
        hadethWithoutTashkel = new Constants(getActivity()).NormalizeCode(hadeth);
        quran = new Constants(getActivity()).parseHTML(this.fileContent, "quran");
        quranWithoutTashkel = new Constants(getActivity()).NormalizeCode(quran);
        parts = new Constants(getActivity()).parseHTML(this.fileContent, "part");
        paragraphsWithoutTashkel = new Constants(getActivity()).NormalizeCode(parts);
        lines = new Constants(getActivity()).parseHTML(this.fileContent, "br");
        lines = new Constants(getActivity()).NormalizeCode(lines);
        other = new Constants(getActivity()).parseHTML(this.fileContent, "ather");
        otherWithoutTashkel = new Constants(getActivity()).NormalizeCode(other);
        sourceInfo = new Constants(getActivity()).parseHTML(this.fileContent, "sourainfo");
        sourceInfoWithoutTashkel = new Constants(getActivity()).NormalizeCode(sourceInfo);
        newFiles = new Constants(getActivity()).parseHTML3(this.fileContent, "div");
        newAyatList = new Constants(getActivity()).arabicNumaricCode(this.supHeaders);
        supHeadersWithoutTashkel = new Constants(getActivity()).NormalizeCode(newAyatList);
        newParagraphs = new Constants(getActivity()).arabicNumaricCode(this.paragraphs);
        paragraphsWithoutTashkel = new Constants(getActivity()).NormalizeCode(newParagraphs);
        customListView = new CustomListView(MainContentPage.getAppContext(), headers, headersWithoutTashkel,
                newAyatList, supHeadersWithoutTashkel, newParagraphs, paragraphsWithoutTashkel,
                hadeth, hadethWithoutTashkel, quran, quranWithoutTashkel,
                parts, partsWithoutTashkel, other, otherWithoutTashkel, lines, linesWithoutTashkel,
                sourceInfo, sourceInfoWithoutTashkel, newFiles);


    }

    private class getDataTask extends AsyncTask<String, Void, String> {
        //
        @Override
        protected void onPreExecute() {
            progressbar.setVisibility(View.VISIBLE);
            Log.d("go excute task","msg");

        }

        @Override
        protected String doInBackground(String... params) {
            fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
            if(sharedFlag==false) {
                sharedFlag=true;
                readTxt(fragVal);// read data
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            sharedFlag=false;
            progressbar.setVisibility(View.INVISIBLE);
            customListView = new CustomListView(MainContentPage.getAppContext(), headers, headersWithoutTashkel,
                    newAyatList, supHeadersWithoutTashkel, newParagraphs, paragraphsWithoutTashkel,
                    hadeth, hadethWithoutTashkel, quran, quranWithoutTashkel,
                    parts, partsWithoutTashkel, other, otherWithoutTashkel, lines, linesWithoutTashkel,
                    sourceInfo, sourceInfoWithoutTashkel, newFiles);
            listView.setAdapter(customListView);

        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void postStatusMessage(String msg, Session session) {

        if(checkPermissions()) {
            Request request = Request.newStatusUpdateRequest(
                    session, msg,
                    new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            if (response.getError() == null)
                                Toast.makeText(getActivity(),
                                        "لقد تمت المشاركة بنجاح",
                                        Toast.LENGTH_LONG).show();
                        }
                    });
            request.executeAsync();
        }
        else
        {
            requestPermissions();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Session.getActiveSession() != null) {
            Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
            Log.d("flag value is ", requestCode + "");
            declinedPerm = Session.getActiveSession().getDeclinedPermissions();
            // Log.d("declined Perm", Session.getActiveSession().getDeclinedPermissions().toString());
            try {

                if (declinedPerm.contains("publish_actions")) {
                    getActivity().finish();
                } else {
                    if (!checkPermissions()) {
                        Toast.makeText(getActivity(), "ادخل اختيارك", Toast.LENGTH_SHORT).show();
                    }
                    postStatusMessage(paragraphs.get(0), Session.getActiveSession());
                }

            } catch (Exception e) {
            }
        }
    }


    public boolean checkPermissions() {
        Session s = Session.getActiveSession();
        if (s != null) {
            return s.getPermissions().contains("publish_actions");
        } else
            return false;
    }

    public void requestPermissions() {
        Session s = Session.getActiveSession();
        if (s != null)
            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
                    this, PERMISSIONS));
    }
    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

}