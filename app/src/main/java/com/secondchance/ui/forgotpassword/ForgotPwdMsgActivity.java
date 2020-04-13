package com.secondchance.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.secondchance.R;
import com.secondchance.ui.login.LoginActivity;

public class ForgotPwdMsgActivity  extends AppCompatActivity implements View.OnClickListener {;
    Button continue_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        setContentView(R.layout.forgotpwd_msg_layout);

        continue_btn= findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.continue_btn)
        {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
