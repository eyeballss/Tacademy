package me.blog.eyeballss.myfirebasetest.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import me.blog.eyeballss.myfirebasetest.R;
import me.blog.eyeballss.myfirebasetest.model.ChatModel;

import static me.blog.eyeballss.myfirebasetest.R.id.left_container;

public class SimpleChatActivity extends RootActivity {

    ListView listView;
    ChatAdapter adapter;
    LayoutInflater layoutInflater;

    Button chatSend;
    EditText chatInput;
    ArrayList<ChatModel> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_chat);

        init();
        setDatabaseListener();

    }

    private void onAddedData(DataSnapshot dataSnapshot){
        ChatModel model = dataSnapshot.getValue(ChatModel.class); //Model 객체를 주면 Model에 담겨서 날아옴.
        items.add(model);
        adapter.notifyDataSetChanged(); //전체를 갈아엎기때문에 비효율적이다.
        listView.setSelection(items.size()-1);
    }

    private void setDatabaseListener() {
        getDatabaseReference().child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) { //자식이 추가되었을 때
                onAddedData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { //자식이 바뀌었을 때

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { //자식이 제거되었을 때

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { //자식이 이동했을 때

            }

            @Override
            public void onCancelled(DatabaseError databaseError) { //자식이.. 캔슬??

            }
        });
    }


    private void init() {
        items= new ArrayList<ChatModel>();

        chatSend = (Button) findViewById(R.id.Button_ChatSend);
        chatInput = (EditText) findViewById(R.id.EditText_ChatInput);

        //system service를 이용해서 inflater를 구할 수 있다!
        //기존에는 Activity.get~~ 으로 가져왔었음.
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = (ListView) findViewById(R.id.Listview_ChatView);
        adapter = new ChatAdapter();
        listView.setAdapter(adapter);

    }

    //메세지 보내기
    public void onSendMessage(View view){
        String msg = chatInput.getText().toString();
        if(TextUtils.isEmpty(msg)) return;

        ChatModel model = new ChatModel();
        model.setEmail(getUser().getEmail());
        model.setMsg(msg);
        model.setRegDate(System.currentTimeMillis()); //GMT 시간 : 런던+9시

        //db에 데이터를 추가.
        getDatabaseReference().child("chat").push() //chat이라는 방에 임의의 값이 생김.
            .setValue(model); //chat이라는 방에 임의의 값이 달린 곳에 내 정보를 보냄.

        chatInput.setText("");
        listView.setSelection(items.size()-1);
    }


    class ChatAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public ChatModel getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        class Holder{
            TextView txt_left_name; //상대 이메일
            TextView txt_left; //상대 내용
//            TextView txt_right_name; //내 이메일
            TextView txt_right; //내 내용
            LinearLayout left_container;
            LinearLayout right_container;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Holder holder;
            if(view==null){
                view = layoutInflater.inflate(R.layout.sendbird_view_group_user_message, viewGroup, false);
                holder=new Holder();
                holder.txt_left = view.findViewById(R.id.txt_left);
                holder.txt_left_name = view.findViewById(R.id.txt_left_name);
                holder.txt_right = view.findViewById(R.id.txt_right);
//                holder.txt_right_name = view.findViewById(R.id.txt_right_name);
                holder.left_container = view.findViewById(left_container);
                holder.right_container = view.findViewById(R.id.right_container);
                view.setTag(holder);
            }else{
                holder = (Holder) view.getTag();
            }

            ChatModel model = getItem(i);
            //내 글이라면
            if(getUser().getEmail().equals(model.getEmail())){
                holder.left_container.setVisibility(View.GONE);
                holder.txt_right.setText(model.getMsg());
            }
            //상대 글이라면
            else{
                holder.right_container.setVisibility(View.GONE);
                holder.txt_left.setText(model.getMsg());
                holder.txt_left_name.setText(model.getEmail());
            }

            return view;
        }
    }
}
