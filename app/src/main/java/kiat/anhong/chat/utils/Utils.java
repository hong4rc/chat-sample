package kiat.anhong.chat.utils;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by Kiat on Oct 17
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Utils {

    public static String getDateDetail(long time) {

        Date date = new Date(time);
        //:TODO check time to show fit
        return new SimpleDateFormat("dd MM, yyyy", Locale.UK).format(date);
    }

}
