package com.secondchance.model;

public class registerrequestmodel {

    String email;
    String password;
    String phone_number;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    String user_name;
    public registerrequestmodel(String email,String password, String phone_num,String user_name)
    {
        this.email = email;
        this.password = password;
        this.phone_number = phone_num;
        this.user_name = user_name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

}
