package kiat.anhong.chat.utils;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by Kiat on Oct 17
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import kiat.anhong.chat.model.RoundImageView;


public class AvatarImageUtils extends HashMap<String, Bitmap> {
    private static AvatarImageUtils instance = new AvatarImageUtils();

    public static AvatarImageUtils getInstance() {
        return instance;
    }

    private AvatarImageUtils() {
    }


    public static void loadImg(final String uid, final RoundImageView imgAvatar) {
        DBHelper.getsInstance().getUserInfoRef().child(uid).child(DBHelper.IMG_B64).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String imgB64 = (String) dataSnapshot.getValue();
                    Bitmap bmp = decodeBitmap(imgB64);
                    imgAvatar.setImageBitmap(bmp);
                    AvatarImageUtils.getInstance().put(uid, bmp);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void loadImg(String uid, String urlAvatar) {
        Bitmap bmp = instance.get(uid);
        if (bmp == null) {
            new LoadImageAsyncTask(uid, instance).execute(urlAvatar);
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
