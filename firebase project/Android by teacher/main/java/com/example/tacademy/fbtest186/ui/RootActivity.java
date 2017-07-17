package com.example.tacademy.fbtest186.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * 내가 만드는 모든 액티비티들의 부모
 * 모든 액티비티가 가져야할 공통 사항을 구현해 둔다
 * ex) 진행율을 표시하는 프로그레스, 변수
 */

public class RootActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ProgressDialog pd;
    public void showPD()
    {
        if( pd == null ){
            pd = new ProgressDialog(this);
            pd.setCancelable(false); // 임의 취소 불가
            pd.setMessage(".. loading ..");
        }
        pd.show();
    }
    public void stopPD(){
        if( pd != null && pd.isShowing() ){
            pd.dismiss();
            //pd = null;
        }
    }
    // 현재 로그인한 유저의 fb 유저 정보
    public FirebaseUser getUser()
    {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
    public void singOut()
    {
        FirebaseAuth.getInstance().signOut();
    }
}












