package com.secondchance.ui.myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.model.forgotpwdchangereponse;
import com.secondchance.model.resetpwdrequestodel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.HomeActivity;
import com.secondchance.ui.login.LoginActivity;
import com.secondchance.utils.Configuration;
import com.secondchance.utils.StorageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {
    SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;
    EditText current_password;
    EditText new_password;
    EditText confirm_password;
    Button done;
    ProgressBar loading;
    ImageView back_img;
    TextInputLayout current_pwd_textinput;
    TextInputLayout newpwd_textinput;
    TextInputLayout confirm_textinput;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.changepwdlayout, container, false);

        secondChanceApplication = (SecondChanceApplication) getActivity().getApplication();
        mStore = StorageUtil.getInstance(getActivity());
        current_password =root. findViewById(R.id.current_password);
        confirm_password = root.findViewById(R.id.confirm_password);
        new_password = root.findViewById(R.id.new_password);
        current_pwd_textinput = root.findViewById(R.id.current_pwd_textinput);
        newpwd_textinput =root. findViewById(R.id.newpwd_textinput);
        confirm_textinput = root.findViewById(R.id.confirm_textinput);
        done =root. findViewById(R.id.next_btn);
        done.setOnClickListener(this);
        loading = root.findViewById(R.id.loading);


        return root;
    }

    private void callreenterpwdwithApi() {
        loading.setVisibility(View.VISIBLE);
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);

            final resetpwdrequestodel resetpwdrequestodel = new resetpwdrequestodel(mStore.getString("emailid"), current_password.getText().toString(), new_password.getText().toString());
            Call<forgotpwdchangereponse> call = api.getreenterpwdResponse(resetpwdrequestodel);

            call.enqueue(new Callback<forgotpwdchangereponse>() {
                @Override
                public void onResponse(Call<forgotpwdchangereponse> call, Response<forgotpwdchangereponse> response) {
                    loading.setVisibility(View.GONE);

                    forgotpwdchangereponse forgotpwdchangereponse = response.body();

                    String success = forgotpwdchangereponse.getSuccess();
                    String data = forgotpwdchangereponse.getData();


                    if (success.equals("true")) {
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (success.equals("false")) {
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Failed to validate Password", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<forgotpwdchangereponse> call, Throwable t) {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Failed to validate OTP", Toast.LENGTH_SHORT).show();


                }

            });

        } else {
            Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.next_btn) {

            boolean iscurrentpass = false;
            boolean isnewpass = false;
            boolean isconfirmpass = false;
            // Check for a valid password.
            if (current_password.getText().toString().isEmpty()) {
                current_pwd_textinput.setError(getResources().getString(R.string.password_error));
                iscurrentpass = false;
            } else if (current_password.getText().length() < 6) {
                current_pwd_textinput.setError(getResources().getString(R.string.error_invalid_password));
                iscurrentpass = false;
            } else {
                iscurrentpass = true;
            }

            if (new_password.getText().toString().isEmpty()) {
                newpwd_textinput.setError(getResources().getString(R.string.password_error));
                isnewpass = false;
            } else if (new_password.getText().length() < 6) {
                newpwd_textinput.setError(getResources().getString(R.string.error_invalid_password));
                isnewpass = false;
            } else {
                isnewpass = true;
            }

            if (confirm_password.getText().toString().isEmpty()) {
                confirm_textinput.setError(getResources().getString(R.string.password_error));
                isconfirmpass = false;
            } else if (confirm_password.getText().length() < 6) {
                confirm_textinput.setError(getResources().getString(R.string.error_invalid_password));
                isconfirmpass = false;
            } else {
                isconfirmpass = true;
            }


            if (isconfirmpass && isnewpass && isconfirmpass) {
                if (new_password.getText().toString().equals(confirm_password.getText().toString())) {
                    if (!new_password.getText().toString().equals(current_password.getText().toString())) {
                        callreenterpwdwithApi();
                    } else {
                        Toast.makeText(getActivity(), "Current password and New password shold be different!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "New password and conformed password shoud be same!", Toast.LENGTH_SHORT).show();
                }


            }


        }
    }
}

