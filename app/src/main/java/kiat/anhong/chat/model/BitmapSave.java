package kiat.anhong.chat.model;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by KiaT on Oct 18
 */

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import kiat.anhong.chat.utils.AvatarImageUtils;

public class BitmapSave {
    private Bitmap bmp;
    private ArrayList<RoundImageView> imageViewList;

    public Bitmap getBitmap() {
        return this.bmp;
    }

    public BitmapSave(String uid) {
        this.imageViewList = new ArrayList<>();
        AvatarImageUtils.getInstance().put(uid, this);
    }

    public void addImgView(RoundImageView imageView) {
        imageViewList.add(imageView);
    }

    public void freeList(Bitmap bmp) {
        Log.d("BitmapSave", "freeList: " + (bmp == null));
        this.bmp = bmp;
        for (RoundImageView img : imageViewList) {
            Log.d("BitmapSave", "freeList: ");
            img.setImageBitmap(this.bmp);
        }
        imageViewList.clear();
    }

}
