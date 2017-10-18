package kiat.anhong.chat.utils;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by KiaT on Oct 17
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import kiat.anhong.chat.model.BitmapSave;


public class AvatarImageUtils extends HashMap<String, BitmapSave> {
    private static final String TAG = "AvatarImageUtils";

    private static AvatarImageUtils instance = new AvatarImageUtils();

    public static AvatarImageUtils getInstance() {
        return instance;
    }

    private AvatarImageUtils() {
    }


    public static void loadImg(final String uid) {
        Log.d(TAG, "loadImg: " + uid);
        DBHelper.getsInstance().getUserInfoRef().child(uid).child(DBHelper.IMG_B64).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String imgB64 = (String) dataSnapshot.getValue();
                    Bitmap bmp = decodeBitmap(imgB64);
                    BitmapSave bmSave = AvatarImageUtils.getInstance().get(uid);
                    bmSave.freeList(bmp);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void loadImg(String uid, String urlAvatar) {
        Log.d(TAG, "loadImg: uid = " + uid + ", urlAvatar = " + urlAvatar);
        BitmapSave bmSave = instance.get(uid);
        if (bmSave == null) {
            bmSave = new BitmapSave(uid);
            new LoadImageAsyncTask(uid, bmSave).execute(urlAvatar);
        }
    }

    public static String encodeBitmap(Bitmap bpm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bpm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBitmap(String imgB64) {
        byte[] decodeByte = Base64.decode(imgB64, 0);
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }
}
