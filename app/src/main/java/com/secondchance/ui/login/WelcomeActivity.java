package com.secondchance.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.secondchance.R;
import com.secondchance.ui.HomeActivity;
import com.secondchance.ui.register.RegisterAppActivity;
import com.secondchance.utils.StorageUtil;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button login_button;
    Button register_button;
    StorageUtil mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadeout,R.anim.fadein);
        setContentView(R.layout.welcome_layout);
        mStore = StorageUtil.getInstance(getApplicationContext());

        if(mStore.getString("DashboardEnabled")!=null&& mStore.getString("DashboardEnabled").equalsIgnoreCase("true"))

        {
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        }

        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);
        login_button.setOnClickListener(this);
        register_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_button) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.register_button) {
            Intent intent = new Intent(this, RegisterAppActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
