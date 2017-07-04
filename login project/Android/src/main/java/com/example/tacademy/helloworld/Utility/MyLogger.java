package com.example.tacademy.helloworld.Utility;

/**
 * Created by Tacademy on 2017-06-29.
 */
public class MyLogger {
    private static MyLogger ourInstance = new MyLogger();

    public static MyLogger getInstance() {
        return ourInstance;
    }

    private MyLogger() {
    }

    public static void log(String key, String value){
        android.util.Log.d(key,value);
    }
}
