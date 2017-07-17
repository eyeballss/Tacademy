package com.example.tacademy.fbtest186.fragment;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class TotalBbsFragment extends RootFragment {
    public TotalBbsFragment() {}

    @Override
    public Query getBbsQuery(DatabaseReference databaseReference) {
        return databaseReference.child("bbs").limitToFirst(100);
    }
}

