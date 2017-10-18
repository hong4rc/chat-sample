package kiat.anhong.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import kiat.anhong.chat.utils.DBHelper;
import kiat.anhong.chat.utils.AvatarImageUtils;

public class SplashActivity extends AppCompatActivity implements ValueEventListener {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        loadAvatar();
        checkLogin();
    }

    private void loadAvatar() {
        DBHelper.getsInstance().getUserInfoRef().addListenerForSingleValueEvent(this);
    }

    private void checkLogin() {
        FirebaseUser mUser = mAuth.getCurrentUser();
        Intent intent;
        if (mUser == null){
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, ChatActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
            String uid = snapshot.getKey();
            String urlAvatar = (String) snapshot
                    .child(DBHelper.USER_INFO )
                    .child(DBHelper.IMG_B64).getValue();

            AvatarImageUtils.loadImg(uid, urlAvatar);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
