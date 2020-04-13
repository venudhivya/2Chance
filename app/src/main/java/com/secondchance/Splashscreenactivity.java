package com.secondchance;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.secondchance.ui.login.WelcomeActivity;
import com.secondchance.utils.Configuration;

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


}


