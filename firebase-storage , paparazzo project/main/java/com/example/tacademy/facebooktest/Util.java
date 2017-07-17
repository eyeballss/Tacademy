package com.example.tacademy.facebooktest;

import android.content.Context;

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
}
