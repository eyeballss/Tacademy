package com.example.tacademy.fbtest186.fragment;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class LikeBbsFragment extends RootFragment {
    public LikeBbsFragment() {
        // Required empty public constructor
    }

    @Override
    public Query getBbsQuery(DatabaseReference databaseReference) {
        return databaseReference.child("bbs").orderByChild("likeCount");
    }
}
