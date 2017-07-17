package com.example.tacademy.facebooktest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.View;
import android.widget.ImageView;

import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profile = (ImageView)findViewById(R.id.profile);

        startActivity(new Intent(this, Main2Activity.class));
    }

    public void onClickPicture(View view){

        //사용자가 선택할 수 있도록 팝업 처리.
        // 선택 사항 : 카메라, 포토 앨범, 파일로 찾기, 닫기기
        Util.getInstance().showPopup3(MainActivity.this,
                "알림",
                "사진을 가져올 방법",
                "카메라",
                new SweetAlertDialog.OnSweetClickListener(){

                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        onCamera();
                    }
                },
                "포토 앨범",
                new SweetAlertDialog.OnSweetClickListener(){
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        onGallery();
                    }
                }
        );

    }

    private void onGallery(){

        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        RxPaparazzo.single(this)
                .crop(options) //사진을 찍고 편집 메뉴를 띄움.
                .usingGallery() //usingGallery는 포토 앨범을 띄움.
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    // See response.resultCode() doc
                    if (response.resultCode() != RESULT_OK) {

                        return;
                    }
                    bind(response.data());
                });
    }


    private void onCamera(){

        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        RxPaparazzo.single(this)
                .crop(options) //사진을 찍고 편집 메뉴를 띄움.
                .usingCamera() //카메라를 띄움
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    // See response.resultCode() doc
                    if (response.resultCode() != RESULT_OK) {

                        return;
                    }
                    bind(response.data());
                });
    }



    void bind(FileData fileData) {
        File file = fileData.getFile();
        if (file != null && file.exists()) {
            Picasso.with(profile.getContext())
                    .load(file)
                    .error(R.mipmap.ic_launcher_round)
                    .into(profile);
        } else {
            Drawable drawable = AppCompatDrawableManager.get().getDrawable(profile.getContext(), R.mipmap.ic_launcher_round);
            profile.setImageDrawable(drawable);
        }
    }
}
