package com.example.ScanPe;

import android.app.Application;

public class GlobalClass extends Application {

    public static String UserID;


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
