package kiat.anhong.chat.utils;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by KiaT on Oct 17
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class LoadImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {
    private static final String TAG = "LoadImageAsyncTask";

    private String uid;
    private AvatarImageUtils avatarImageUtils;
    private static DBHelper dbHelper = DBHelper.getsInstance();

    LoadImageAsyncTask(String uid, AvatarImageUtils avatarImageUtils) {
        this.uid = uid;
        this.avatarImageUtils = avatarImageUtils;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url;
        Bitmap bmp = null;
        try {
            Log.d(TAG, "doInBackground: " + strings[0]);
            url = new URL(strings[0]);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            bmp = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        if (bmp != null) {
            this.avatarImageUtils.put(uid, bmp);
            String imgB64 = AvatarImageUtils.encodeBitmap(bmp);
            dbHelper.getUserInfoRef().child(uid).child(DBHelper.IMG_B64).setValue(imgB64);


        }
    }
}
