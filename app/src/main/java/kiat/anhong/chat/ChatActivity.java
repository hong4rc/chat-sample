package kiat.anhong.chat;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kiat.anhong.chat.adapter.MessageAdapter;
import kiat.anhong.chat.model.Message;
import kiat.anhong.chat.utils.DBHelper;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference msgRef;
    private DBHelper dbHelper;

    private RecyclerView mRecyclerViewChat;
    private ImageButton btnSend;
    private EditText edtMsg;

    private List<Message> msgList;
    private MessageAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
        getWidget();
        setWidget();
        listenDataChanged();
    }

    private void listenDataChanged() {
        msgRef.orderByChild(DBHelper.TIME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addMessage(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addMessage(DataSnapshot dataSnapshot) {
        Message msg = dataSnapshot.getValue(Message.class);
        assert msg != null;
        if (msg.content.equals("") || msg.userUID.equals("") || msg.timeSend == 0) {
            Toast.makeText(this, "ff", Toast.LENGTH_SHORT).show();
            return;
        }
        msgList.add(msg);
        scrollToEnd(true);
    }

    private void scrollToEnd(boolean notify) {
        int index = adapter.getItemCount() - 1;
        if (notify) {
            adapter.notifyItemInserted(index);
        }
        mRecyclerViewChat.scrollToPosition(index);
    }

    private void init() {
        dbHelper = DBHelper.getsInstance();
        msgRef = dbHelper.getMsgListRef();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        try {
            dbHelper.updateUserInfo(mUser);
        } catch (IOException e) {
            Toast.makeText(this, "Can't find your avatar", Toast.LENGTH_SHORT).show();
        }

        msgList = new ArrayList<>();
        adapter = new MessageAdapter(msgList);
        layoutManager = new LinearLayoutManager(getApplicationContext());
    }

    private void getWidget() {
        mRecyclerViewChat = (RecyclerView) findViewById(R.id.recyclerViewChat);
        btnSend = (ImageButton) findViewById(R.id.btnSend);
        edtMsg = (EditText) findViewById(R.id.edtMsg);

    }

    private void setWidget() {
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerViewChat.setLayoutManager(layoutManager);
        mRecyclerViewChat.setAdapter(adapter);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String mMsg = edtMsg.getText().toString().trim();
        edtMsg.setText("");
        if (mMsg.length() <= 0) {
            return;
        }
        Message msg = new Message();
        msg.content = mMsg;
        msg.userUID = mUser.getUid();
        msg.timeSend = Calendar.getInstance().getTimeInMillis();

        dbHelper.getMsgListRef().push().setValue(msg);

    }
}
