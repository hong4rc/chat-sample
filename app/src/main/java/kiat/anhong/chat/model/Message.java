package kiat.anhong.chat.model;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by Kiat on Oct 17
 */

import java.util.List;

public class Message {
    public String userUID;
    public String content;
    public long timeSend;
    public List<String> likeList;

    public Message(){

    }
}
