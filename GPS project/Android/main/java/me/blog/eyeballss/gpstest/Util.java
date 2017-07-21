package me.blog.eyeballss.gpstest;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.util.Log;


import com.squareup.otto.Bus;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.R.attr.value;
import static java.lang.Double.parseDouble;

/**
 * Created by Tacademy on 2017-07-17.
 */

public class Util {
    private static final Util ourInstance = new Util();

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {}

    //팝업
    public void showPopup3(Context context, String title, String message,
                           String cName, SweetAlertDialog.OnSweetClickListener cEvent,
                           String oName, SweetAlertDialog.OnSweetClickListener oEvent){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText(cName)
                .setConfirmClickListener(cEvent)
                .setCancelText(oName)
                .setCancelClickListener(oEvent)
                .show();
    }

    SweetAlertDialog pDialog;
    public SweetAlertDialog showLoading(Context context){
        return showLoading(context, "Loading");
    }

    public SweetAlertDialog showLoading(Context context, String msg){
        return showLoading(context, msg, "#A5DC86");
    }

    public SweetAlertDialog showLoading(Context context, String msg, String color){
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor(color));
        pDialog.setTitleText(msg);
        pDialog.setCancelable(false);
        pDialog.show();
        return pDialog;
    }

    public void showSimplePopup(Context context, String title, String content, int type){
        new SweetAlertDialog(context, type)
                .setTitleText(title)
                .setContentText(content)
                .show();
    }

    public SweetAlertDialog showPopup(Context context, String title, String content, int type){
        SweetAlertDialog alert = new SweetAlertDialog(context, type);
        alert.setTitleText(title)
                .setContentText(content)
                .setCancelable(false);
        alert.show();
        return alert;
    }


    private boolean log = true;
    public void Log(String key, String main){
        if(log){
            Log.d(key, "-----------------------------------------------------------------");
            Log.d(key, "["+value+"]");
            Log.d(key, "-----------------------------------------------------------------");
        }
    }

    public void Log(String key, int value){
        if(log){
            Log.d(key, "=================================================================");
            Log.d(key, "["+value+"]");
            Log.d(key, "=================================================================");
        }
    }
    public void Log(String key, String value[]){
        if(log){
            Log.d(key, "=================================================================");
            for(String i : value){
                Log.d(key, "- ["+i+"] \n");
            }
            Log.d(key, "=================================================================");
        }
    }

    public void Log(String key, int value[]){
        if(log){
            Log.d(key, "=================================================================");
            for(int i : value){
                Log.d(key, "- ["+i+"] \n");
            }
            Log.d(key, "=================================================================");
        }
    }

    //Address를 받아서 시 군 구 동 까지 표시하는 메소드
    public String getTransferAddr(Address address){
        if(address==null) return "";

        return String.format("%s %s %s", address.getAdminArea(), address.getLocality(), address.getThoroughfare());
    }



    //Otto Bus 를 만들어서 서비스에서 얻은 gps 정보를 Main 에서 가져오도록 하자!
    Bus gpsBusFromService = new Bus();

    public Bus getGpsBusFromService() {
        return gpsBusFromService;
    }



    //좌표 변환. KAREC - > GEO로 변환
    public GeoPoint transFromKATEC2GEO(GeoPoint point){
        return GeoTrans.convert(
                GeoTrans.KATEC, //from
                GeoTrans.GEO, //to
                point //이용할 포인트
        );
    }

    public double transToDouble(String str){
        try{
             return Double.parseDouble(str);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }

    }




    //paparazzo 이용하는 부분
//    private void onGallery(Context context){
//
//        UCrop.Options options = new UCrop.Options();
//        options.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
//
//        //single 로 하면 하나만 고를 수 있음.
//        RxPaparazzo.single(context)
//                .crop(options) //사진을 찍고 편집 메뉴를 띄움.
//                .usingGallery() //usingGallery는 포토 앨범을 띄움.
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    // See response.resultCode() doc
//                    if (response.resultCode() != RESULT_OK) {
//
//                        return null;
//                    }
//                    bind(response.data());
//                });
//    }
//    private void onCamera(){
//
//        UCrop.Options options = new UCrop.Options();
//        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
//
//        RxPaparazzo.single(this)
//                .crop(options) //사진을 찍고 편집 메뉴를 띄움.
//                .usingCamera() //카메라를 띄움
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    // See response.resultCode() doc
//                    if (response.resultCode() != RESULT_OK) {
//
//                        return;
//                    }
//                    bind(response.data());
//                });
//    }
//    void bind(FileData fileData) {
//        File file = fileData.getFile();
//        if (file != null && file.exists()) {
//            Picasso.with(profile.getContext())
//                    .load(file)
//                    .error(R.mipmap.ic_launcher_round)
//                    .into(profile);
//        } else {
//            Drawable drawable = AppCompatDrawableManager.get().getDrawable(profile.getContext(), R.mipmap.ic_launcher_round);
//            profile.setImageDrawable(drawable);
//        }
//    }
}
