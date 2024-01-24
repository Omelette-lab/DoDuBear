package com.example.denglu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
public class RestArea extends AppCompatActivity {
    private String username;
    private ImageView todolist,my;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        this.username=getIntent().getStringExtra("name");
        todolist=findViewById(R.id.todolist);
        my=findViewById(R.id.my_message);
        initView();
        initData();//数据
    }
    //任务表
    public void todolist(View v){
        Intent intent=new Intent();
        intent.setClass(RestArea.this,RecyclerViewActivity.class);
        intent.putExtra("name",this.username);
        startActivity(intent);
    }
    //我的信息
    public void my_message(View v){
        Intent intent=new Intent();
        intent.setClass(RestArea.this,My_message.class);
        intent.putExtra("name",this.username);
        startActivity(intent);
    }

    private void initView(){
        recyclerView=findViewById(R.id.recyclerview);
    }
    private void initData(){
        List<Video> videlist=new ArrayList<>();
       videlist.add(new Video("八佰","https://vt1.doubanio.com/202011121422/402a7cf332c7a8872b26f34f1da66f7e/view/movie/M/402670047.mp4",R.mipmap.icon_1));
       videlist.add(new Video("恶棍之都","https://vt1.doubanio.com/202011121427/bb698fcd0365ee8949c40989187cdcd7/view/movie/M/402660947.mp4",R.mipmap.icon_2));
       videlist.add(new Video("一点到家","https://vt1.doubanio.com/202011121429/3b7edd7e79d1abac319c5d364c1a4571/view/movie/M/402670105.mp4",R.mipmap.icon_3));
       videlist.add(new Video("误杀","https://vt1.doubanio.com/202011121431/6e7c24efddca20768d54a1a3fbc58cec/view/movie/M/402560730.mp4",R.mipmap.icon_4));
       videlist.add(new Video("一出好戏","https://vt1.doubanio.com/202011121435/0fcf83d2b7fc80124c1713431929be02/view/movie/M/402340740.mp4",R.mipmap.icon_5));
       videlist.add(new Video("我不是药神","https://vt1.doubanio.com/202011121748/c5e407af3a99d15062b7b389c364e204/view/movie/M/402330315.mp4",R.mipmap.icon_6));
        videlist.add(new Video("无邪","https://vt1.doubanio.com/202011121949/8b9f4e93c9865a8584e3bfc9b00c9673/view/movie/M/402680124.mp4",R.mipmap.icon_7));

       //适配器
        VideoAdapter adapter=new VideoAdapter(videlist,this.username);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
