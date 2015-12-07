package com.google.android.gms.example.bannerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by Abanoub Wagdy on 3/24/2015.
 */

public class SplashScreen extends Activity {

    double x = 4.8;
    double y = 6.5;
    Timer timer;
    ImageView ivLink;
    Context context;
    final static public String PREFS_NAME = "PREFS_NAME";
    final static private String PREF_KEY_SHORTCUT_ADDED = "PREF_KEY_SHORTCUT_ADDED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_splach_screen);
            ivLink = (ImageView) findViewById(R.id.ivSplash);
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            timer.cancel();
                            startActivity(new Intent(getApplicationContext(), MainActivityPage.class));
                            finish();

                        }

                    });
                }
            }, 2000);

        // this code used for getting facebook hashkey
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(
			        "com.google.android.gms.example.bannerexample",
			        PackageManager.GET_SIGNATURES);
		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for (Signature signature : info.signatures) {
            MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            md.update(signature.toByteArray());
            Log.d("KeyHashsssssssssssssssssss:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
    }







}