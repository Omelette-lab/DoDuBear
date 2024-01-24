package com.example.denglu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
public class Play extends AppCompatActivity {
    private TextView my_videoname;
    private VideoView my_videoview;
    private Button btn;
    private MediaController mediaController;
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
        initData();


    }
    private void initView(){
        my_videoname=findViewById(R.id.my_videoname);
        my_videoview=findViewById(R.id.my_videoview);
        mediaController=new MediaController(this);
        btn=findViewById(R.id.btn);
    }
    private void initData(){
        String video_name=getIntent().getStringExtra("video_name");
        String video_Url=getIntent().getStringExtra("video_Url");
        this.account=getIntent().getStringExtra("name");
        my_videoname.setText(video_name);
        my_videoview.setVideoPath(video_Url);
        my_videoview.setMediaController(mediaController);
        mediaController.setMediaPlayer(my_videoview);
        my_videoview.start();
    }
    public void back(View v){
        Intent intent=new Intent();
        intent.setClass(Play.this, RestArea.class);
        intent.putExtra("name",this.account);
        startActivity(intent);
    }
}
