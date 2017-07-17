package com.example.tacademy.facebooktest;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static java.lang.System.load;

/*
 * 왼쪽 사용자 메뉴의 프로필 사진 클릭 -> 카메라, 포토앨범, 사진 고를 수 있음.
 * 어떤 것을 선택해도 골라 사진을 편집해 가져옴.
 * 가져온 것을 파이어베이스 저장소로 업로드.
 * 업로드 성공시 프로필 사진도 교체!(기본 세팅이 파일이 아닌, URL로 띄움)
 */
public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CircleImageView profile;
    ImageView background;
    //화면 구성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //상단 툴바
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//      토글을 누르면 사이드 메뉴 나옴. back key나 토글 다시 누르면 사이드 메뉴 사라짐
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //사이드메뉴. 각종 메뉴를 선택시 onNavigationItemSelected() 가 호출되게 하는 리스너.
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //사이드 메뉴에 있는 뷰에 접근하는 방법.
        profile = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        background = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.background);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //사용자가 선택할 수 있도록 팝업 처리.
                // 선택 사항 : 카메라, 포토 앨범, 파일로 찾기, 닫기기
                Util.getInstance().showPopup3(Main2Activity.this,
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
        });


    }

    //사이드바 닫기와 앱 종료 기능.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //사용자 정의 코드

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
        //로딩바를 불러옴
        final SweetAlertDialog dialog = Util.getInstance().showLoading(this);

        //이제 파이어베이스로 감.
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://myfirebasetest-68394.appspot.com/");

        //업로드 할 파일 준비. uri로 변환
        File myFile = fileData.getFile();
        Uri file = Uri.fromFile(myFile);

        //업로드 경로. 여기서의 path는 images/[file의 이름] 이 됨.
        StorageReference riversRef = storageReference.child("images/"+myFile.getName());
        //파일의 이름과 경로들.
        Log.d("Main2", myFile.getName()); //파일 이름
        Log.d("Main2", myFile.getAbsolutePath()); //절대 경로
        Log.d("Main2", myFile.getPath()); //경로

        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            //성공했을 때

            //성공하면 꼭 갖고 있어야 할 값들 :
            // - "images/"+myFile.getName() , 즉 경로값. 삭제할 때 필요함.
            // - 1번 값의 url 값(갖고 있는게 편의상 유리)
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //url 획득 -> 데이터베이스 갱신 -> 화면 갱신!
                String downloadUrl = taskSnapshot.getDownloadUrl().toString(); //업로드된 이미지의 url을 받음. 빨간 불은 단지 '경고'의 의미임.

                //피카소에서 uri, 혹은 실제 url 를 이용하여 이미지를 띄우자
//                Picasso.with(getBaseContext()).load(downloadUrl).into(profile);
                Picasso.with(getBaseContext())
                        .load(downloadUrl)
                        .centerCrop()
                        .fit()
                        .into(background);


                //파일 업로드 하고 사진이 세팅되면 dismiss
                dialog.dismissWithAnimation();
                Util.getInstance().showSimplePopup(Main2Activity.this, "알림!", "세팅 성공!", SweetAlertDialog.SUCCESS_TYPE);

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    //실패했을 때
                    @Override
                    public void onFailure(@NonNull Exception e) {



                        //파일 업로드 하고 사진이 세팅되면 dismiss
                        dialog.dismissWithAnimation();
                        Util.getInstance().showSimplePopup(Main2Activity.this, "알림!", "FB로 사진 업로드 실패", SweetAlertDialog.ERROR_TYPE);
                    }
                });


    }


}
