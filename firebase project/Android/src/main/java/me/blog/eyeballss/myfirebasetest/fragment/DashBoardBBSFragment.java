package me.blog.eyeballss.myfirebasetest.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.blog.eyeballss.myfirebasetest.R;

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

    //  리사이클러 뷰                리스트뷰
    //  CardView를 cell로 씀.        그냥 만들어서 씀.
    //  RecyclerView.Adapter         BaseAdaper
    //  viewHolder 기본 장착         우리가 직접 장착
    //   (RecyclerView.viewHolder)
    //  3개의 방향성있음.            위에서 아래로 단방향성
    //   (선형, 그리드형, 높은 자유도의 ex확장형)
    RecyclerView recyclerView;
    public DashBoardBBSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dash_board_bb, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView_Recycler);

        return view;
    }

    //프래그먼트의 라이프사이클 메소드.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //리사이클러뷰의 방향성 설정. setLayoutManager 메소드로 설정 가능.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}
