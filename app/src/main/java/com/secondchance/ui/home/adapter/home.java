
package com.secondchance.ui.home.adapter;


import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class home {
    Activity activity;

    private ArrayList<String> usernameList;
    private ArrayList<String> dobList;
    private ArrayList<String> imageList;
    String useraction;


    public home(Activity activity, ArrayList<String> usernameList, ArrayList<String> dobList, ArrayList<String> imageList, String useraction) {
        this.activity = activity;
        this.dobList = dobList;
        this.imageList = imageList;
        this.usernameList = usernameList;
        this.useraction = useraction;

    }

    public ArrayList<String> getUsernameList() {
        return usernameList;
    }

    public void setUsernameList(ArrayList<String> usernameList) {
        this.usernameList = usernameList;
    }

    public ArrayList<String> getDobList() {
        return dobList;
    }

    public void setDobList(ArrayList<String> dobList) {
        this.dobList = dobList;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getUseraction() {
        return useraction;
    }

    public void setUseraction(String useraction) {
        this.useraction = useraction;
    }

}
