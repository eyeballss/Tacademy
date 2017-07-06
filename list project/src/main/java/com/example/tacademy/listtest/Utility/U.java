package com.example.tacademy.listtest.Utility;

import android.util.Log;

/**
 * Created by Tacademy on 2017-07-06.
 */

public class U {
    private static final U ourInstance = new U();

    public static U getInstance() {
        return ourInstance;
    }

    private U() {
    }

    public void log(String msg){
        Log.d("TTTT", msg);
    }
}
