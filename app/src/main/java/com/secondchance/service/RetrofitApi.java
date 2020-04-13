package com.secondchance.service;



import com.google.gson.JsonObject;
import com.secondchance.model.forgotpwdchangereponse;
import com.secondchance.model.forgotpwdreponsemodel;
import com.secondchance.model.forgotpwdrequestmodel;
import com.secondchance.model.loginreponsemodel;
import com.secondchance.model.loginrequestmodel;
import com.secondchance.model.otpvalidaterequestmodel;
import com.secondchance.model.registerrequestmodel;
import com.secondchance.model.registerresponsemodel;
import com.secondchance.model.resetpwdrequestodel;
import com.secondchance.model.resetpwdwithOTPModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetrofitApi {

    @POST("createUser")
    Call<registerresponsemodel> getregisterResponse(@Body registerrequestmodel jsonObject);


    @POST("login")
    Call<loginreponsemodel> getloginResponse(@Body loginrequestmodel jsonObject);

    @POST("forgetPassword")
    Call<forgotpwdreponsemodel> getforgotpwdResponse(@Body forgotpwdrequestmodel jsonObject);


    @POST("validateOtp")
    Call<forgotpwdreponsemodel> getotpvalidateResponse(@Body otpvalidaterequestmodel jsonObject);



    @POST("resetPassword")
    Call<forgotpwdchangereponse> getresetpwdwithOTPResponse(@Body resetpwdwithOTPModel jsonObject);



    @POST("changePassword")
    Call<forgotpwdchangereponse> getreenterpwdResponse(@Body resetpwdrequestodel jsonObject);




}
