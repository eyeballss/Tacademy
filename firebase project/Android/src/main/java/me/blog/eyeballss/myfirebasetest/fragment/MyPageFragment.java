package me.blog.eyeballss.myfirebasetest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.blog.eyeballss.myfirebasetest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {
    public MyPageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        return view;
    }

}
