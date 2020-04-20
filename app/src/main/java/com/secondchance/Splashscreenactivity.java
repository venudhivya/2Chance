package com.secondchance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.secondchance.ui.login.WelcomeActivity;
import com.secondchance.utils.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Splashscreenactivity extends Activity {


    private SharedPreferences mAppPref;
    private boolean isFirstTime = true;


    /**
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splashscreen);
        mAppPref = getSharedPreferences(Configuration.FirstTime, 0);
        isFirstTime = mAppPref.getBoolean(Configuration.FirstTime, true);
//        AnalyticsUtils.getInstance().postEvents(getApplicationContext(), AnalyticsConstants.EVENT_APP_OPENED, "", "","");
printHashKey(getApplicationContext());
        if (isFirstTime) {

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    moveNextPage(WelcomeActivity.class);
                }
            }, 2000);
        }
        // Set the Secure flag for this Window - DATA THEOREM FIX
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    /**
     * @param class1
     */
    private void moveNextPage(Class<? extends Activity> class1) {
        startActivity(new Intent(this, class1));
        finish();
    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }
    }
}


