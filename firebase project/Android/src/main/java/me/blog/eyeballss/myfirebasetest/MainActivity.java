package me.blog.eyeballss.myfirebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.blog.eyeballss.myfirebasetest.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.d("YouTube", "시작합니다.");
//        new MyThread().start();
//        Log.d("YouTube", "끝났습니다.");

//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d("FCM Main", "onTokenRefresh:"+ refreshedToken);
//
//
//        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5004735687657408~1119835172");
//
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        Intent intent = new Intent(this, LoginActivity.class);
//        Intent intent = new Intent(this, Video.class);
        startActivity(intent);

    }


    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();

            try{
                String result = downloadUrl("https://drive.google.com/file/d/0B0B9BrncUmShMkZlZ0ZlTU84NlE/preview");
                Log.d("YouTube", result);
            }catch(Exception e){Log.d("YouTube", "ERRRRRRRRRRRRRRRRROR");e.printStackTrace();}
        }
    }


    public String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            String contentAsString = readIt(is);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
                Log.d("YouTube", "close");

            }
        }
    }

    public String readIt(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("fmt_stream_map")) {
                sb.append(line + "\n");
                break;
            }
        }
        reader.close();
        String result = decode(sb.toString());
        String[] url = result.split("\\|");
        return url[1];
    }

    public String decode(String in) {
        String working = in;
        int index;
        index = working.indexOf("\\u");
        while (index > -1) {
            int length = working.length();
            if (index > (length - 6)) break;
            int numStart = index + 2;
            int numFinish = numStart + 4;
            String substring = working.substring(numStart, numFinish);
            int number = Integer.parseInt(substring, 16);
            String stringStart = working.substring(0, index);
            String stringEnd = working.substring(numFinish);
            working = stringStart + ((char) number) + stringEnd;
            index = working.indexOf("\\u");
        }
        return working;
    }
}
