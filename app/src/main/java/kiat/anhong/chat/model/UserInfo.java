package kiat.anhong.chat.model;
/*
 * Copyright (C) 2017 The KiaT Project
 * Created by Kiat on Oct 17
 */

public class UserInfo {
    public String name;
    public String uid;
    public String imgB64;
    public UserInfo(String imgB64){
        this.imgB64 = imgB64;
    }
    public UserInfo(String uid, String imgB64){
        this.imgB64 = imgB64;
        this.uid = uid;
    }
    public UserInfo(){
    }
}
