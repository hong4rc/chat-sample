package kiat.anhong.chat.utils;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by KiaT on Oct 19
 */

import java.util.Calendar;

public class TimeStamp {
    private static TimeStamp instance;
    private long timeLast;
    private long timeNew;

    public static TimeStamp getInstance() {
        if (instance == null) {
            instance = new TimeStamp();
        }
        return instance;
    }

    private TimeStamp() {
        this.timeLast = Calendar.getInstance().getTimeInMillis();
        this.timeNew = this.timeLast;
    }

    public long getTimeLast() {
        return this.timeLast;
    }

    public long getTimeNew() {
        return this.timeNew;
    }

    public void setTimeLast(long timeLast) {
        this.timeLast = timeLast;
    }

    public void setTimeNew(long timeNew) {
        this.timeNew = timeNew;
    }
}
