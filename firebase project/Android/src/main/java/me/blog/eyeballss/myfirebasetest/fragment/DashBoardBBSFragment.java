package me.blog.eyeballss.myfirebasetest.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import me.blog.eyeballss.myfirebasetest.R;
import me.blog.eyeballss.myfirebasetest.fragment_holder.PostViewHolder;
import me.blog.eyeballss.myfirebasetest.model.Post;
import me.blog.eyeballss.myfirebasetest.ui.DetailActivity;

/**
 * A simple {@link Fragment} subclass.
 *
 * 프래그먼트란, 액티비티 내에 존재.
 * UI나 작용(행위)등을 수행하는 부분(조각).
 * 화면에 대응하고,
 * 라이프사이클이 존재.
 * 액티비티 위에서만 존재.
 * 액티비티의 전체 화면 혹은 일부분의 화면으로 존재.
 */
public class DashBoardBBSFragment extends RootFragment {

    public DashBoardBBSFragment() {
        // Required empty public constructor
    }

    @Override
    public Query getDatabaseQuery(DatabaseReference databaseReference) {
        return databaseReference.child("bbs").limitToFirst(100);
    }


}