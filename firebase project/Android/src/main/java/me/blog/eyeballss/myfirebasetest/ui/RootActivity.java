package me.blog.eyeballss.myfirebasetest.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tacademy on 2017-07-05.
 */

public class RootActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    protected DatabaseReference getDatabaseReference(){
        if(databaseReference==null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    FirebaseUser getUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    protected void signOut(){
        if(FirebaseAuth.getInstance()!=null){
            FirebaseAuth.getInstance().signOut();
        }
    }

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
