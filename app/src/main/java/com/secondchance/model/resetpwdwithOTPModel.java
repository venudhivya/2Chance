package com.secondchance.model;

public class resetpwdwithOTPModel {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    String password;

    public resetpwdwithOTPModel(String email,String password)
    {
        this.email = email;
        this.password = password;
    }
}
