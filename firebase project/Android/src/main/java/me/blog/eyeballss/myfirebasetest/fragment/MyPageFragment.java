package me.blog.eyeballss.myfirebasetest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import me.blog.eyeballss.myfirebasetest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends RootFragment {

    public MyPageFragment() {
        // Required empty public constructor
    }

    @Override
    public Query getDatabaseQuery(DatabaseReference databaseReference) {
        return databaseReference.child("mybbs").child(getMyUid()).limitToFirst(100);
    }

}
