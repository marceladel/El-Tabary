package com.google.android.gms.example.bannerexample.facebook_share;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jsoup.helper.HttpConnection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.google.android.gms.example.bannerexample.R;
import com.google.android.gms.example.bannerexample.com.example.constant.Constants;
import com.google.android.gms.example.bannerexample.com.example.constant.StoreData;


public class FaceBookShare extends FragmentActivity {


    private LoginButton loginBtn;

    private String userId;

    private TextView userName;
    AsyncFacebookRunner mAsyncRunner;
    SharedPreferences preferences;

    private UiLifecycleHelper uiHelper;
    String activityFlag;

    private static final List<String> PERMISSIONS = Arrays
            .asList("publish_actions");

    private static String message = "Sample status posted from android app";
    SharedPreferences sharedPreferences;
    Editor editor;
    String AccessToken;
    String sharedData;
    ImageView userImage;
    List<String> declinedPerm=new ArrayList<String>();
    boolean backClick=false;
    int backButtonCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_face_book_share);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Intent get = getIntent();
        sharedData = get.getStringExtra("data");
        userImage = (ImageView) findViewById(R.id.userImage);

//        try {
//            userAccessToken=settings.getString("accessToken","");
//            Log.d("accessTokenInshare", userAccessToken);
//
//        } catch (Exception e) {
//            // TODO: handle exception
//        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        userName = (TextView) findViewById(R.id.user_name);
        loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            // boolean flag2=sharedPreferences.getBoolean("flag", false);

            if (state.isOpened()) {

                Log.d("FacebookSampleActivity", "Facebook session opened");
                final String AccessToken = session.getAccessToken();

                loginBtn.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
                    @Override
                    public void onUserInfoFetched(GraphUser user) {
                        if (user != null) {
                            userName.setText("Hello, " + user.getName());
                            userId = user.getId();
                            new StoreData(getApplicationContext()).saveAccessToken(AccessToken);
                            userImage.setImageBitmap(getFacebookProfilePicture(userId));
                            Log.d("userid", userId);
                        }
                    }
                });

                Log.d("statusCallback AccessToken", AccessToken);


            } else if (state.isClosed()) {

                Log.d("FacebookSampleActivity", "Facebook session closed");
            }
        }

    };


//    public void buttonsEnabled(boolean isEnabled) {
//        postImageBtn.setEnabled(isEnabled);
//    }


    public void postStatusMessage(String msg) {
        if (checkPermissions()) {
            Log.d("check prem", "msg");
            Request request = Request.newStatusUpdateRequest(
                    Session.getActiveSession(), msg,
                    new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            if (response.getError() == null)
                                Toast.makeText(FaceBookShare.this,
                                        "لقد تمت المشاركة بنجاح",
                                        Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
            request.executeAsync();
        } else {
            Log.d("request perm", "msg");
            requestPermissions();
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
        Log.d("requestPerm", "msg");
        Session s = Session.getActiveSession();

        if (s != null) {
            Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
                    this, PERMISSIONS);
            s.requestNewPublishPermissions(newPermissionsRequest);
            boolean flag= s.isPermissionGranted(PERMISSIONS.toString());
            Log.d("flag",flag+"");
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
        // buttonsEnabled(Session.getActiveSession().isOpened());
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


    // get facebook profile pic
    public static Bitmap getFacebookProfilePicture(String userID) {
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Bitmap bitmap = null;
        try {

            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Session.getActiveSession() != null) {
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
            Log.d("flag value is ", requestCode + "");
            declinedPerm=Session.getActiveSession().getDeclinedPermissions();
            // Log.d("declined Perm", Session.getActiveSession().getDeclinedPermissions().toString());
            try {

                if(declinedPerm.contains("publish_actions")) {
                    finish();
                }
                else {
                    if(!checkPermissions()) {
                        Toast.makeText(FaceBookShare.this, "ادخل اختيارك", Toast.LENGTH_SHORT).show();
                    }
                    postStatusMessage(sharedData);
                }



            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        backClick=true;
        finish();
    }
    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }


}
