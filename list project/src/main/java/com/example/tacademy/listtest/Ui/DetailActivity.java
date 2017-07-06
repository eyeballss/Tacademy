package com.example.tacademy.listtest.Ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.tacademy.listtest.Model.DaumSearchImageModel.Channel.*;

import com.example.tacademy.listtest.Model.DaumSearchImageModel;
import com.example.tacademy.listtest.R;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        ArrayList<Items> data = (ArrayList<Items>)intent.getSerializableExtra("data");
        int index = intent.getIntExtra("index", 0);
        webView = (WebView)findViewById(R.id.WebView_web);

        webView.loadUrl(data.get(index).link);
        Log.d("DetailActivity", "link : "+data.get(index).link);

    }
}
