package com.example.tacademy.listtest.Ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by Tacademy on 2017-07-05.
 */

public class RootActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ProgressDialog pd;

    public void showPd(){
        if(pd==null){
            pd = new ProgressDialog(this);
            pd.setMessage("loading");
            pd.setCancelable(false); // 중간에 임의 취소 불가
        }
        pd.show();
    }

    public void stopPd(){
        if(pd!=null && pd.isShowing()){
            pd.dismiss();
//            pd=null;
        }
    }

}
