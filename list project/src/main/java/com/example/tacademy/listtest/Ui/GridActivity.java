package com.example.tacademy.listtest.Ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.listtest.Model.DaumSearchImageModel;
import com.example.tacademy.listtest.NET.Net;
import com.example.tacademy.listtest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.tacademy.listtest.Utility.*;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridActivity extends RootActivity {

    GridActivity self;
    EditText searchInput;
    GridView gridView;
    SearchAdapter searchAdapter;
    ArrayList<DaumSearchImageModel.Channel.Items> items;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    TextView search_summary;
    ImageButton changBtn;
    int columnCount;
    int pageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.activity_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        items           = new ArrayList();
        initImageLoader();
        pageNumber=1;
        columnCount=3;
        search_summary  = (TextView)findViewById(R.id.TextView_searchSummary);
        searchInput     = (EditText)findViewById(R.id.EditText_search);
        gridView = (GridView)findViewById(R.id.gridview);
        changBtn = (ImageButton)findViewById(R.id.Button_viewChange);
        searchAdapter   = new SearchAdapter();
        gridView.setAdapter(searchAdapter);

    }
    public void initImageLoader()  {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                //.bitmapConfig(Bitmap.Config.RGB_565)
                //.bitmapConfig(Bitmap.Config.ARGB_4444)
                .build();
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this.getBaseContext()).build();
        //ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).build();
        imageLoader.init(configuration);
    }

    class SearchAdapter extends BaseAdapter {
//        int flag=10;
        @Override
        public int getCount() {
            return items.size();//%flag;
        }
        @Override
        public DaumSearchImageModel.Channel.Items getItem(int i) {
            return items.get(i);
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }

        class Holder3 {
            ImageView poster;
            TextView title;
        }
        class Holder1 {
            ImageView poster;
            TextView title;
            TextView comment;
        }
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(i==items.size()-2 && pageNumber<=3) {
                pageNumber++;
                sendQuery();
            }
            if(view==null){
                if(columnCount>2) {
                    view    = self.getLayoutInflater().inflate(R.layout.gridview_item, viewGroup, false);
                    Holder3 holder3 = new Holder3();
                    holder3.poster = view.findViewById(R.id.ImageView_image);
                    holder3.title = view.findViewById(R.id.TextView_title);

                    view.setTag(holder3);
                }
                else {
                    view    = self.getLayoutInflater().inflate(R.layout.listview_item, viewGroup, false);
                    Holder1 holder1 = new Holder1();
                    holder1.poster = view.findViewById(R.id.ImageView_starIcon);
                    holder1.title = view.findViewById(R.id.TextView_title);
                    holder1.comment = view.findViewById(R.id.TextView_comment);

                    view.setTag(holder1);
                }


            }else{
                if(columnCount>2) {
                    Holder3 holder3 = (Holder3) view.getTag();
                    holder3.title.setText(Html.fromHtml(String.valueOf(Html.fromHtml(items.get(i).title))));
                    holder3.poster.setVisibility(View.VISIBLE);
                    imageLoader.displayImage(items.get(i).thumbnail, holder3.poster, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    });
                }
                else {
                    Holder1 holder1 = (Holder1) view.getTag();

                    holder1.title.setText(Html.fromHtml(String.valueOf(Html.fromHtml(items.get(i).title))));
                    holder1.comment.setText(items.get(i).pubDate+" "+items.get(i).cpname);
                    imageLoader.displayImage(items.get(i).thumbnail, holder1.poster, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    });
                }
            }


            return view;
        }
    }


    private void sendQuery(){

        Map<String, String> map = new HashMap<String, String>();
        map.put("apikey","35b00ac25f224b73bb64599c0e5041e5");
        map.put("q", searchInput.getText().toString());
        map.put("output", "json");
        map.put("pageno", String.valueOf(pageNumber));
        map.put("result", "20");

        Call<DaumSearchImageModel> res = Net.getInstance().getMemberFactoryIm().searchImg(map);
        res.enqueue(new Callback<DaumSearchImageModel>() {       // --------------------------- E
            @Override
            public void onResponse(Call<DaumSearchImageModel> call, Response<DaumSearchImageModel> response) {   // ---------- F
                if(response.isSuccessful()){
                    if(response.body() != null){ //null 뿐 아니라 오류 값이 들어올 때도 처리해줘야 함.

                        Log.d("TTTT", "성공!");

                        // DO SOMETHING HERE with users!
                        jsonParsing(response.body());


                    }else{
                        Log.d("TTTT", "실패 1 response 내용이 없음");
                    }
                }else{
                    Log.d("TTTT", "실패 2 서버 에러");
                }
                stopPd();
            }

            @Override
            public void onFailure(Call<DaumSearchImageModel> call, Throwable t) {    //------------------G
                Log.d("TTTT", "실패 3 통신 에러 "+t.getLocalizedMessage());
                stopPd();
            }

        });
    }

    public void onSearch(View view){
        pageNumber=1;
        sendQuery();
    }

    private void jsonParsing(DaumSearchImageModel body) {
//        ArrayList<String> thumnails = new ArrayList<String>();
//        for(int i=0; i<body.channel.item.size(); i++){
//            Log.d("TTTT", body.channel.item);
//        }
        if(body.channel.totalCount==0){
            search_summary.setText("[ "+searchInput.getText().toString()+" ]의 검색 결과가 없습니다.");
        }else{
            search_summary.setText("검색 결과 : "+body.channel.totalCount+"개");
        }
        if(pageNumber==1) items = body.channel.item;
        else {
            for(DaumSearchImageModel.Channel.Items data : body.channel.item){
                items.add(data);
            }
        }
//        searchAdapter.setThumbnail(body.channel.item);
        searchAdapter.notifyDataSetChanged();
    }

    public void onChange(View view){
        if(columnCount<2) columnCount+=2;
        else columnCount-=2;

        gridView.setNumColumns(columnCount);
//        searchAdapter=null;
//        searchAdapter   = new SearchAdapter();
        gridView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }
}
