package com.secondchance.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.secondchance.MainActivity;
import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.model.data;
import com.secondchance.model.forgotpwdreponsemodel;
import com.secondchance.model.forgotpwdrequestmodel;
import com.secondchance.model.loginreponsemodel;
import com.secondchance.model.loginrequestmodel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.login.LoginActivity;
import com.secondchance.utils.Configuration;
import com.secondchance.utils.StorageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button next_btn;
    EditText phone_num;
    ImageView img_forgot_pwd;
    LinearLayout linear_email;
    SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;
    ProgressBar loading;
    ImageView back_img;
    TextInputLayout phonenum_textinputlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        setContentView(R.layout.forgot_pwd_layout);
        secondChanceApplication = (SecondChanceApplication) getApplicationContext();
        phonenum_textinputlayout = findViewById(R.id.phonenum_textinputlayout);

        mStore = StorageUtil.getInstance(getApplicationContext());
        loading = findViewById(R.id.loading);
        next_btn = findViewById(R.id.next_btn);
        phone_num = findViewById(R.id.phone_num);
        img_forgot_pwd = findViewById(R.id.img_forgot_pwd);
        linear_email = findViewById(R.id.linear_email);
        next_btn.setOnClickListener(this);
        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);

    }

    private void callForgotPwdApi() {
        loading.setVisibility(View.VISIBLE);
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);

            final forgotpwdrequestmodel forgotpwdrequestmodel = new forgotpwdrequestmodel(phone_num.getText().toString());
            Call<forgotpwdreponsemodel> call = api.getforgotpwdResponse(forgotpwdrequestmodel);

            call.enqueue(new Callback<forgotpwdreponsemodel>() {
                @Override
                public void onResponse(Call<forgotpwdreponsemodel> call, Response<forgotpwdreponsemodel> response) {
                    loading.setVisibility(View.GONE);

                    forgotpwdreponsemodel forgotpwdreponsemodel = response.body();

                    String success = forgotpwdreponsemodel.getSuccess();
                    data data = forgotpwdreponsemodel.getData();
                    mStore.setString("emailid", data.getEmail());

                    if (success.equals("true")) {
                        Toast.makeText(getApplicationContext(), "OTP sent successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), ForgotPwdOTPActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (success.equals("false")) {
                        Toast.makeText(getApplicationContext(), "OTP not sent", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to sent email", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<forgotpwdreponsemodel> call, Throwable t) {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed to sent email", Toast.LENGTH_SHORT).show();


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
            boolean isPhoneValid = false;

            // Check for a valid phone number.
            if (phone_num.getText().toString().isEmpty()) {
                phonenum_textinputlayout.setError(getResources().getString(R.string.phone_error));
                isPhoneValid = false;
            } else if (phone_num.getText().length() < 10 || phone_num.getText().length() > 10) {
                phonenum_textinputlayout.setError(getResources().getString(R.string.error_invalid_phone));
            } else {
                isPhoneValid = true;
            }


            if (isPhoneValid) {
                callForgotPwdApi();
            }

        } else if (id == R.id.back_img) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}