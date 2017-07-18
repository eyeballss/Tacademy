package me.blog.eyeballss.gpstest;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;


import cn.pedant.SweetAlert.SweetAlertDialog;
/**
 * Created by Tacademy on 2017-07-17.
 */

class Util {
    private static final Util ourInstance = new Util();

    static Util getInstance() {
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

    public void Log(String key, String value){
        if(true){
            Log.d(key, "-----------------------------------------------------------------");
            Log.d(key, ""+value);
            Log.d(key, "-----------------------------------------------------------------");
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
