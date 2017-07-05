package com.example.tacademy.listtest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.listtest.Model.DaumSearchImageModel;
import com.example.tacademy.listtest.NET.Net;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Service extends RootActivity {

    ListView listView;
    ListViewAdapter listViewAdapter;
    EditText searchBar;
    Button searchBtn;
    ImageButton changeBtn;
    TextView searchSummary;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        setListView();
        test();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void onSearch(View view){

        if(view.getId()==R.id.EditText_search){
            searchBar.setText("");
            return;
        }

        String searchWord = searchBar.getText().toString();
        if(searchWord.trim().equals("")) {
            Toast.makeText(this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        searchBar.clearFocus();
        showPd();

        Map<String, String> map = new HashMap<String, String>();
        map.put("apikey","35b00ac25f224b73bb64599c0e5041e5");
        map.put("q", searchWord);
        map.put("output", "json");

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

    private void jsonParsing(DaumSearchImageModel body) {
//        ArrayList<String> thumnails = new ArrayList<String>();
//        for(int i=0; i<body.channel.item.size(); i++){
//            Log.d("TTTT", body.channel.item);
//        }
        if(body.channel.totalCount==0){
            searchSummary.setText("[ "+searchBar.getText().toString()+" ]의 검색 결과가 없습니다.");
        }else{
            searchSummary.setText("검색 결과 : "+body.channel.totalCount+"개");
        }
        listViewAdapter.setThumbnail(body.channel.item);
        listViewAdapter.notifyDataSetChanged();
    }

    public void onChange(View view){
        stopPd();
    }




    private void test() {
        String json= "[{ \"number\" : \"3\",\n" +
                "  \"title\" : \"hello_world\"  \n" +
                "},\n" +
                "{ \"number\" : \"2\",\n" +
                "  \"title\" : \"hello_waldo\"  \n" +
                "}]";
        Gson gson = new Gson();

        Elements[] elements= gson.fromJson(json, Elements[].class);
        for(Elements e: elements){
            Log.d("TTTT", e.number+" "+e.title);
        }

    }
    class Elements{
        String number;
        String title;
    }


    private void setListView() {
        listView.setAdapter(listViewAdapter);
    }

    private void init() {
        //리스트뷰
        listView = (ListView)findViewById(R.id.listview);
        String[][] data =
                {{"	챔피언스 리그 직행	", "첼시 FC"},
                        {"챔피언스 리그 직행", "토트넘 핫스퍼 FC	"},
                        {"챔피언스 리그 직행", "맨체스터 시티 FC	"},
                        {"챔피언스 리그 예선", "리버풀 FC"},
                        {"유로파 리그", "아스널 FC"},
                        {"유로파 리그 우승으로 챔피언스 리그 직행", "맨체스터 유나이티드 FC"},
                        {"7	", "에버턴 FC"},
                        {"8	", "사우샘프턴 FC"},
                        {"9	", "AFC 본머스"},
                        {"10", "웨스트 브로미치 알비온 FC"},
                        {"11", "웨스트햄 유나이티드 FC"},
                        {"12", "레스터 시티 FC"},
                        {"13", "스토크 시티 FC"},
                        {"14", "크리스탈 팰리스 FC"},
                        {"15", "스완지 시티 AFC"},
                        {"16", "번리 FC"},
                        {"17", "왓포드 FC"},
                        {"18강등 직행", "헐 시티 AFC"},
                        {"19강등 직행", "미들즈브러 FC"},
                        {"20강등 직행", "선덜랜드 AFC"}
                };
        listViewAdapter= new ListViewAdapter(this, data);
        searchBar = (EditText)findViewById(R.id.EditText_search);
        searchBtn = (Button)findViewById(R.id.Button_search);
        changeBtn = (ImageButton)findViewById(R.id.Button_viewChange);
        searchSummary = (TextView)findViewById(R.id.TextView_searchSummary);
    }

}
