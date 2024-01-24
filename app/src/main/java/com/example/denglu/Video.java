package com.example.denglu;

public class Video {
    private int icon;
    private String videName;
    private String videoUrl;
    public Video(String videName,String videoUrl,int icon){
        this.videName=videName;
        this.videoUrl=videoUrl;
        this.icon=icon;
    }
    public String getVideName(){
        return this.videName;
    }
    public String getVideoUrl(){
        return this.videoUrl;
    }
    public int getIcon(){
        return this.icon;
    }
}
