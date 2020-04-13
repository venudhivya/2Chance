package com.secondchance.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.secondchance.MainActivity;
import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.model.forgotpwdchangereponse;
import com.secondchance.model.forgotpwdreponsemodel;
import com.secondchance.model.otpvalidaterequestmodel;
import com.secondchance.model.resetpwdwithOTPModel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.login.LoginActivity;
import com.secondchance.utils.Configuration;
import com.secondchance.utils.StorageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPwdNewpwdActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linear_pwd;
    Button next_btn;
    TextInputLayout password_textlayout;
    TextInputLayout repassword_text_input;
    TextInputEditText password;
    TextInputEditText re_password;
    SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;
    ProgressBar loading;
    ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        secondChanceApplication = (SecondChanceApplication) getApplicationContext();
        mStore = StorageUtil.getInstance(getApplicationContext());
        setContentView(R.layout.forgotpwd_new_layout);
        repassword_text_input = findViewById(R.id.repassword_text_input);
        password_textlayout = findViewById(R.id.password_textinputlayout);
        password = findViewById(R.id.password);
        re_password = findViewById(R.id.re_password);
        linear_pwd = findViewById(R.id.linear_pwd);
        next_btn = findViewById(R.id.next_btn);
        loading = findViewById(R.id.loading);
        next_btn.setOnClickListener(this);
        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);

    }

    private void callpwdwithOTPApi() {
        loading.setVisibility(View.VISIBLE);
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);

            final resetpwdwithOTPModel otpvalidaterequestmodel = new resetpwdwithOTPModel(mStore.getString("emailid"), password.getText().toString());
            Call<forgotpwdchangereponse> call = api.getresetpwdwithOTPResponse(otpvalidaterequestmodel);

            call.enqueue(new Callback<forgotpwdchangereponse>() {
                @Override
                public void onResponse(Call<forgotpwdchangereponse> call, Response<forgotpwdchangereponse> response) {
                    loading.setVisibility(View.GONE);

                    forgotpwdchangereponse forgotpwdchangereponse = response.body();

                    String success = forgotpwdchangereponse.getSuccess();
                    String data = forgotpwdchangereponse.getData();


                    if (success.equals("true")) {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), ForgotPwdMsgActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (success.equals("false")) {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to validate OTP", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<forgotpwdchangereponse> call, Throwable t) {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed to validate OTP", Toast.LENGTH_SHORT).show();


                }

            });

        } else {
            Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.next_btn) {
            boolean ispass = false;
            boolean isreenterpass = false;
            // Check for a valid password.
            if (password.getText().toString().isEmpty()) {
                password_textlayout.setError(getResources().getString(R.string.password_error));
                ispass = false;
            } else if (password.getText().length() < 6) {
                password_textlayout.setError(getResources().getString(R.string.error_invalid_password));
                ispass = false;
            } else {
                ispass = true;
            }

            if (re_password.getText().toString().isEmpty()) {
                repassword_text_input.setError(getResources().getString(R.string.password_error));
                isreenterpass = false;
            } else if (re_password.getText().length() < 6) {
                repassword_text_input.setError(getResources().getString(R.string.error_invalid_password));
                isreenterpass = false;
            } else {
                isreenterpass = true;
            }


            if (ispass && isreenterpass) {
                if (password.getText().toString().equals(re_password.getText().toString())) {
                    callpwdwithOTPApi();
                } else {
                    Toast.makeText(getApplicationContext(), "New password and conformed password shoud be the same!", Toast.LENGTH_SHORT).show();
                }


            }


        } else if (id == R.id.back_img) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}