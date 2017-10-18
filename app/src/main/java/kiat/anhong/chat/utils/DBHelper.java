package kiat.anhong.chat.utils;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by KiaT on Oct 17
 */

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class DBHelper {

    public static final String USER_INFO = "user_info";
    public static final String IMG_B64 = "imgB64";
    public static final String ROOM_CHAT = "room_chat";
    public static final String TIME = "timeSend";
    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();

    private static DBHelper sInstance = new DBHelper();

    public static DBHelper getsInstance() {
        return DBHelper.sInstance;
    }

    public DatabaseReference getUserInfoRef() {
        return this.dataBase.child(USER_INFO);
    }

    public DatabaseReference getMsgListRef() {
        return this.dataBase.child(ROOM_CHAT);
    }

    private DBHelper() {
    }

    public void updateUserInfo(FirebaseUser user) throws IOException {
        Uri photoUri = user.getPhotoUrl();
        assert photoUri != null;
        AvatarImageUtils.loadImg(user.getUid(), photoUri.toString());
    }
}
