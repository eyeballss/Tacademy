package me.blog.eyeballss.myfirebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;

import me.blog.eyeballss.myfirebasetest.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM Main", "onTokenRefresh:"+ refreshedToken);


        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5004735687657408~1119835172");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
//        finish();
    }
}
