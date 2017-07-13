package me.blog.eyeballss.myfirebasetest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import me.blog.eyeballss.myfirebasetest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeBBSFragment extends RootFragment {


    public LikeBBSFragment() {
        // Required empty public constructor
    }

    @Override
    public Query getDatabaseQuery(DatabaseReference databaseReference) {
        return databaseReference.child("bbs").orderByChild("likeCount"); //likeCount 값을 정렬의 기준으로 삼아 가져와라.
    }

}
