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
import me.blog.eyeballss.myfirebasetest.ui.BBSActivity;
import me.blog.eyeballss.myfirebasetest.ui.DetailActivity;
import me.blog.eyeballss.myfirebasetest.ui.RootActivity;

/**
 * A simple {@link Fragment} subclass.
 */


public abstract class RootFragment extends Fragment {

    //  리사이클러 뷰                리스트뷰
    //  CardView를 cell로 씀.        그냥 만들어서 씀.
    //  RecyclerView.Adapter         BaseAdaper
    //  viewHolder 기본 장착         우리가 직접 장착
    //   (RecyclerView.viewHolder)
    //  3개의 방향성있음.            위에서 아래로 단방향성
    //   (선형, 그리드형, 높은 자유도의 ex확장형)

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Post, PostViewHolder> firebaseRecyclerAdapter; //이 리사이클러 어댑터는


    public RootFragment() {
        // Required empty public constructor
    }


    //프래그먼트 라이프 사이클의 onCreate 다음 단계
    //여기서 recyclerView를 인플레이트 하자.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dash_board_bb, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView_Recycler);

        return view;
    }

    //프래그먼트의 라이프사이클의 onCreateView 다음 단계.
    //여기서 recycler view의 방향성을 정하고 어댑터를 적용하자.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //리사이클러뷰의 방향성 설정. setLayoutManager 메소드로 설정 가능.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //리사이클러뷰의 어댑터 연결!
        // T는 Type을 의미.
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(
                Post.class, //여기서 넣어준 Post.class가 아래 populateViewHolder에서 모델로 쓰여질 것임. 즉, 아이템 하나의 모델!
                R.layout.recycler_item_dashboard, //item layout
                PostViewHolder.class,  //여기서 넣어준 PostViewHolder.class가 아래 populateViewHolder에서 뷰홀더로 쓰여질 것임. 즉, 아이템 하나에 대한 뷰 홀더!
                getDatabaseQuery(FirebaseDatabase.getInstance().getReference())
//                FirebaseDatabase.getInstance().getReference().child("bbs").limitToFirst(100)
            ) {
            //자 이제 파이어베이스 리사이클러뷰에서 필요한 메소드를 재정의하자.
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {

                //해당 글의 키(데이터의 근본이 되는 줄기값)를 구해서 씀.
                //이 키는 위에 Query(getDatabaseQuery의 결과) 로부터 가져오는 것 같다.
                final DatabaseReference KEY  = getRef(position); //이것으로 해당 글의 키를 갖고 올 수 있는 참조값을 들고옴.

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //전체 게시글에 대한 클릭 리스너.
                        //누르면 상세보기가 됨.
                        //해당 글의 키(데이터의 근본이 되는 줄기값)를 구해서 씀.

                        //참조 경로의 문자값을 보내자. String 형식의 실제 키 값임.
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtra("key", KEY.getKey());
                        startActivity(intent);

                    }
                });

                //내가 좋아요를 했다면
                if(model.getLike().containsKey(getMyUid())){
                    viewHolder.like.setImageResource(android.R.drawable.presence_online);
                }else{ //아니라면
                    viewHolder.like.setImageResource(android.R.drawable.presence_invisible);
                }

                //데이터를 받아서 view holder에 세팅.
                viewHolder.toBind(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //좋아요 아닐때 누르면 불이 켜지고 좋아요일때 누르면 불이 꺼짐
                        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
                        //dbReference를 구했고, 업데이트 대상
                        DatabaseReference target = root.child("bbs").child(KEY.getKey());
                        updateLikeState(target);
                        DatabaseReference target2 = root.child("mybbs")
                                .child(getMyUid()).child(KEY.getKey());
                        updateLikeState(target2);
                    }
                });
            }

        };
        //세팅된 어댑터를 넣으면 그대로 돌아간다고 함. 뷰홀더 등 내가 조작할 필요 없음.
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public String getMyUid() {
        return ((BBSActivity) getActivity()).getUser().getUid();
    }

    //좋아요 눌렀을 때 db를 바꾸는 메소드.
    private void updateLikeState(DatabaseReference target) {

        // 해당 줄기에 트랜잭션을 건다!!!!
        target.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {


                // 그 후에 데이터를 뽑아야지
                Post data = mutableData.getValue(Post.class);
                if( data == null ){
                    return Transaction.success(mutableData);
                }

                // 전에 좋아요 한 적이 있는지 없는지 먼저 처리.
                // 값 변경(좋아요 반전)
                String uid = getMyUid();
                if(data.getLike().containsKey(uid)){
                    data.getLike().remove(uid);
                    data.setLikeCount(data.getLikeCount()-1);
                }else{
                    data.getLike().put(uid, true);
                    data.setLikeCount(data.getLikeCount()+1);
                }

                // 데이터를 다시 설정
                mutableData.setValue(data);


                return Transaction.success(mutableData);
            }

            //완료 된 후.
            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // 트랜잭션을 풂.
                if(databaseError == null){
                    Toast.makeText(getContext(), "좋아요 추가 성공!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "좋아요 추가 실패애! : ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public abstract Query getDatabaseQuery(DatabaseReference databaseReference);
}
