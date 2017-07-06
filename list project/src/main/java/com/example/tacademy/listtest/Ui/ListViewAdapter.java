package com.example.tacademy.listtest.Ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.listtest.Model.DaumSearchImageModel;
import com.example.tacademy.listtest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2017-07-04.
 */

public class ListViewAdapter extends BaseAdapter {

    DisplayImageOptions options;
    ImageLoader imageLoader;
    private String [][] data;
    ArrayList<DaumSearchImageModel.Channel.Items> thumbnails;
    Service activity;
    ListViewAdapter(Activity activities, String[][] data){
        this.data = data;
        activity = (Service)activities;
        initImageLoader();
    }

    public List getData(){
        return thumbnails;
    }
    @Override
    public int getCount() {

        if(thumbnails!=null) return thumbnails.size();
        return data.length;
    }

    @Override
    public String[] getItem(int i) {

        if(thumbnails!=null) return null;
        return data[i];
    }

    //안 쓰는 녀석이라고 함.
    public long getItemId(int i) {
        return 0;
    }

    public void setThumbnail(List l){
        thumbnails = (ArrayList)l;
    }

    class ViewHolder{
        ImageView poster;
        TextView title;
        TextView comment;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if(view==null){
            view =  activity.getLayoutInflater().inflate(R.layout.listview_item, viewGroup, false);
            holder = new ViewHolder();
            holder.poster = view.findViewById(R.id.ImageView_starIcon);
            holder.title = view.findViewById(R.id.TextView_title);
            holder.comment = (TextView)view.findViewById(R.id.TextView_comment);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }


        holder.title.setText(data[i][1]);
        holder.comment.setText(data[i][0]);

        if(thumbnails==null) return view;

        holder.title.setText(Html.fromHtml(String.valueOf(Html.fromHtml(thumbnails.get(i).title))));
        holder.comment.setText(thumbnails.get(i).pubDate+" "+thumbnails.get(i).cpname);

//        imageLoader.displayImage("http://13.59.72.237:3000/images/"+(i-1)+".png", poster, new ImageLoadingListener(){
        imageLoader.displayImage(thumbnails.get(i).thumbnail, holder.poster, new ImageLoadingListener(){


            @Override
            public void onLoadingStarted(String imageUri, View view) { //로딩이 시작됨.

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) { //로딩이 실패함.
                holder.poster.setImageDrawable(null); //url 에서 못 가져오면 사진 자체를 비워둠.
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) { //로딩 완료됨.

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) { //로딩이 취소됨.

            }
        });




        return view;
    }

    private void initImageLoader(){
        //이미지 로더
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(activity.getBaseContext()).build();
        imageLoader.init(configuration);
    }
}
