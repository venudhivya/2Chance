package com.secondchance.model;

public class forgotpwdrequestmodel {


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    String phone_number;

    public forgotpwdrequestmodel(String phone_number)
    {
        this.phone_number = phone_number;
    }

}
