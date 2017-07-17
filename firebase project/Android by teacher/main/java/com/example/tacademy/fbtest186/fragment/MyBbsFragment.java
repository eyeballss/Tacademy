package com.example.tacademy.fbtest186.fragment;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyBbsFragment extends RootFragment {
    public MyBbsFragment() {
        // Required empty public constructor
    }
    @Override
    public Query getBbsQuery(DatabaseReference databaseReference) {
        return databaseReference.child("mybbs").child(getUid()).limitToFirst(100);
    }
}








